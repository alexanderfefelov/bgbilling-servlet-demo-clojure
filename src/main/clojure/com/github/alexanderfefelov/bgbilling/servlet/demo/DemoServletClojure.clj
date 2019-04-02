(ns com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServletClojure
  (:import
    org.apache.log4j.Logger
    bitel.billing.common.VersionInfo
    ru.bitel.common.logging.NestedContext)
  (:gen-class
    :extends javax.servlet.http.HttpServlet
    :main false
    :exposes-methods {
      doGet parentDoGet,
      doPost parentDoPost,
      doPut parentDoPut,
      doDelete parentDoDelete,
      doHead parentDoHead,
      doOptions parentDoOptions,
      doTrace parentDoTrace}))

(def ^:private logContext "servlet")

(def ^:private logger (Logger/getLogger "DemoServletClojure"))

(defmacro wrap [block]
  `(try
    (NestedContext/push logContext)
    (~@block)
    (catch Exception e# (.. logger (error e#)))
    (finally (NestedContext/pop))))

(defn -init [this config]
  (wrap(.. logger (trace "init"))))

(defn -destroy [this]
  (wrap(.. logger (trace "destroy"))))

(defn -doGet [this request response]
  (wrap(
    (.. logger (trace "doGet"))
    (def kernelVer (VersionInfo/getVersionInfo "server"))
    (def message (format "Hello, World!\n%s %s"
      (.. kernelVer getModuleName)
      (.. kernelVer getVersionString)))
    (.. response getWriter (println message)))))

(defn -doPost [this request response]
  (wrap(
    (.. logger (trace "doPost"))
    (.parentDoPost this request response))))

(defn -doPut [this request response]
  (wrap(
    (.. logger (trace "doPut"))
    (.parentDoPut this request response))))

(defn -doDelete [this request response]
  (wrap(
    (.. logger (trace "doDelete"))
    (.parentDoDelete this request response))))

(defn -doHead [this request response]
  (wrap(
    (.. logger (trace "doHead"))
    (.parentDoHead this request response))))

(defn -doOptions [this request response]
  (wrap(
    (.. logger (trace "doOptions"))
    (.parentDoOptions this request response))))

(defn -doTrace [this request response]
  (wrap(
    (.. logger (trace "doTrace"))
    (.parentDoTrace this request response))))
