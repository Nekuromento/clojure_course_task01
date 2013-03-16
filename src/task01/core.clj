(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn tag? [element]
  (keyword? (tag element)))

(defn r-class? [element]
  (= (:class (attributes element)) "r"))

(defn extract-links [element]
  (map #(:href (attributes %))
        (filter #(= (tag %) :a) (children element))))

(defn get-links []
  (let [data (parse "clojure_google.html")]
    (loop [links []
           elements (list data)]
      (if (empty? elements)
        links
        (let [element (first elements)]
          (recur (if (r-class? element)
                   (concat links (extract-links element))
                   links)
                 (concat (next elements) (filter tag? (children element)))))))))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))

