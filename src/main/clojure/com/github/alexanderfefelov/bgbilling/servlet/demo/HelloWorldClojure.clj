(ns com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldClojure
  (:import
    org.apache.log4j.Logger
    ru.bitel.common.logging.NestedContext)
  (:gen-class
    :extends javax.servlet.http.HttpServlet
    :exposes-methods {
      doGet parentDoGet,
      doPost parentDoPost,
      doPut parentDoPut,
      doDelete parentDoDelete,
      doHead parentDoHead,
      doOptions parentDoOptions,
      doTrace parentDoTrace}))

(def logContext "servlet")

(def logger (Logger/getLogger "HelloWorldClojure"))

(defmacro wrap [block] `
  (try
    (do
      (NestedContext/push logContext)
      (~@block))
    (finally
      (NestedContext/pop))))

(defn -init [this config]
  (wrap
    (.. logger (trace "init"))))

(defn -destroy [this]
  (wrap
    (.. logger (trace "destroy"))))

(defn -doGet [this request response]
  (wrap
    (do
      (.. logger (trace "doGet"))

      (.. response getWriter (println "Hello, World!")))))

(defn -doPost [this request response]
  (wrap
    (do
      (.. logger (trace "doPost"))
      (.parentDoPost this request response))))

(defn -doPut [this request response]
  (wrap
    (do
      (.. logger (trace "doPut"))
      (.parentDoPut this request response))))

(defn -doDelete [this request response]
  (wrap
    (do
      (.. logger (trace "doDelete"))
      (.parentDoDelete this request response))))

(defn -doHead [this request response]
  (wrap
    (do
      (.. logger (trace "doHead"))
      (.parentDoHead this request response))))

(defn -doOptions [this request response]
  (wrap
    (do
      (.. logger (trace "doOptions"))
      (.parentDoOptions this request response))))

(defn -doTrace [this request response]
  (wrap
    (do
      (.. logger (trace "doTrace"))
      (.parentDoTrace this request response))))
