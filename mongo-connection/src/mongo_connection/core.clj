(ns mongo-connection.core
  (:gen-class)
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))


(defn- transform-id-to-string [document]
  "replaces mongo _id object to an string id object"
  (if-let [id (:_id document)]
    (assoc document :_id (.toString id)))) 

(defn get-mongo-conn [host port] 
  "creates a new mongo connection without any authentication"
  (mg/connect {:host host :port port}))

(defn get-mongo-db [mongo-conn db-name]
  (mg/get-db mongo-conn db-name))

(defn get-all [db collection]
  (map transform-id-to-string(mc/find-maps db collection {})))

(defn -main
  ""
  [& args]
  (let [mongo-conn (get-mongo-conn "localhost" 27017)
        db (get-mongo-db mongo-conn "tweets")
        collection "twitter"]
    (print (get-all db collection))))