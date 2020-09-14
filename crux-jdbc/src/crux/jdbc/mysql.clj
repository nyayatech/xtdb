(ns ^:no-doc crux.jdbc.mysql
  (:require [crux.jdbc :as j]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as jdbcr]
            [clojure.tools.logging :as log]))

(defn- check-tx-time-col [pool]
  (when-not (= "timestamp"
               (-> (jdbc/execute-one! pool
                                      ["SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'tx_events' AND COLUMN_NAME = 'tx_time'"]
                                      {:builder-fn jdbcr/as-unqualified-lower-maps})
                   :data_type))
    (log/warn (str "`tx_time` column not in UTC format. "
                   "See https://github.com/juxt/crux/releases/tag/20.09-1.12.0 for more details."))))

(defn ->dialect [_]
  (reify j/Dialect
    (db-type [_] :mysql)
    (setup-schema! [_ ds]
      (jdbc/execute! ds ["
CREATE TABLE IF NOT EXISTS tx_events (
  event_offset INT AUTO_INCREMENT PRIMARY KEY,
  event_key VARCHAR(255),
  tx_time TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
  topic VARCHAR(255) NOT NULL,
  v LONGBLOB NOT NULL,
  compacted INTEGER NOT NULL)"])

      (when (zero? (-> (jdbc/execute! ds ["
SELECT COUNT(1) IdxPresent
FROM INFORMATION_SCHEMA.STATISTICS
WHERE table_schema=DATABASE() AND table_name='tx_events' AND index_name='tx_events_event_key_idx'"])
                       first
                       :IdxPresent))

        (jdbc/execute! ds ["CREATE INDEX tx_events_event_key_idx ON tx_events(compacted, event_key)"]))

      (check-tx-time-col ds))))
