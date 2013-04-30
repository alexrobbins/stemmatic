(defproject stemmatic "0.1.0-SNAPSHOT"
  :description "Automatically compute document lineages"
  :url "https://github.com/alexrobbins/stemmatic"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]]
  :plugins [[lein-ring "0.8.3"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :ring {:handler stemmatic.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :main stemmatic.core)
