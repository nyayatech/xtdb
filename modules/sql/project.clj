(defproject com.xtdb/xtdb-sql "<inherited>"
  :description "SQL for XTDB using Apache Calcite"

  :plugins [
            [lein-parent "0.3.8"]]

  :parent-project {:path "../../project.clj"
                   :inherit [:version :repositories :deploy-repositories
                             :managed-dependencies
                             :pedantic? :global-vars
                             :license :url :pom-addition]}

  :scm {:dir "../.."}
  :dependencies [[org.clojure/clojure]
                 [com.xtdb/xtdb-core]
                 [pro.juxt.clojars-mirrors.cheshire/cheshire]
                 [org.apache.calcite/calcite-core "1.22.0" :exclusions [com.google.code.findbugs/jsr305]]
                 [org.apache.calcite.avatica/avatica-server "1.16.0"]]

  :profiles {:dev {:dependencies [[ch.qos.logback/logback-classic]]}
             :test {:dependencies [[com.xtdb/xtdb-test]]}}

  :java-source-paths ["src"]
  :javac-options ["-source" "8" "-target" "8"
                  "-XDignore.symbol.file"
                  "-Xlint:all,-options,-path"
                  "-Werror"
                  "-proc:none"]

  :jvm-opts ["-Dlogback.configurationFile=../../resources/logback-test.xml"
             "-Dclojure.spec.compile-asserts=true"
             "-Dclojure.spec.check-asserts=true"]


  :classifiers {:sources {:prep-tasks ^:replace []}
                })
