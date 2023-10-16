(defproject com.xtdb/xtdb-kafka "<inherited>"
  :description "XTDB Kafka"

  :plugins [
            [lein-parent "0.3.8"]]

  :parent-project {:path "../../project.clj"
                   :inherit [:version :repositories :deploy-repositories
                             :managed-dependencies
                             :pedantic? :global-vars
                             :license :url :pom-addition]}

  :scm {:dir "../.."}

  :dependencies [[org.clojure/clojure]
                 [org.clojure/tools.logging]
                 [com.xtdb/xtdb-core]
                 [org.apache.kafka/kafka-clients "2.6.1" :exclusions [org.lz4/lz4-java
                                                                      org.xerial.snappy/snappy-java]]
                 [org.xerial.snappy/snappy-java "1.1.8.4"]
                 [pro.juxt.clojars-mirrors.cheshire/cheshire]
                 [com.cognitect/transit-clj nil :exclusions [org.msgpack/msgpack]]]

  :profiles {:dev {:dependencies [[ch.qos.logback/logback-classic]]}}

  :java-source-paths ["src"]
  :javac-options ["-source" "8" "-target" "8"
                  "-Xlint:all,-options,-path"
                  "-Werror"
                  "-proc:none"]


  :classifiers {:sources {:prep-tasks ^:replace []}
                })
