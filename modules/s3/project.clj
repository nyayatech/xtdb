(defproject com.xtdb/xtdb-s3 "<inherited>"
  :description "XTDB S3 integration"

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
                 [software.amazon.awssdk/s3]]

  :profiles {:test {:dependencies [[com.xtdb/xtdb-test]]}}

  :jvm-opts ["-Dlogback.configurationFile=../../resources/logback-test.xml"]

  :java-source-paths ["src"]
  :javac-options ["-source" "8" "-target" "8"
                  "-XDignore.symbol.file"
                  "-Xlint:all,-options,-path"
                  "-Werror"
                  "-proc:none"]


  :classifiers {:sources {:prep-tasks ^:replace []}
                })
