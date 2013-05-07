(defproject stemmatic "0.1.0-SNAPSHOT"
  :description "Automatically compute document lineages"
  :url "https://github.com/alexrobbins/stemmatic"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.priority-map "0.0.2"]
                 [compojure "1.1.5"]
                 [net.sf.jung/jung2 "2.0.1" :extension "pom"]]
  :plugins [[lein-ring "0.8.3"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :ring {:handler stemmatic.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :main stemmatic.core)
