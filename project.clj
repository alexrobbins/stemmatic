(defproject stemmatic "0.1.0-SNAPSHOT"
  :description "Automatically compute document stemmata"
  :url "https://github.com/alexrobbins/stemmatic"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.priority-map "0.0.2"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]]
  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.3"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :ring {:handler stemmatic.web.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :cljsbuild
  {:builds
   [{:source-paths ["src/cljs"]
     :compiler {:output-to "resources/public/js/main.js"
                :optimizations :whitespace
                :pretty-print true}}]}
  :main stemmatic.cli)
