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

(defn -init [this config]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "init"))
    (finally
      (NestedContext/pop))))

(defn -destroy [this]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "destroy"))
    (finally
      (NestedContext/pop))))

(defn -doGet [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doGet"))
    (def kernelVer (VersionInfo/getVersionInfo "server"))
    (def message (format "Hello, World!\n%s %s"
      (.. kernelVer getModuleName)
      (.. kernelVer getVersionString)))
    (.. response
        getWriter
          (println message))
    (finally
      (NestedContext/pop))))

(defn -doPost [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doPost"))
    (.parentDoPost this request response)
    (finally
      (NestedContext/pop))))

(defn -doPut [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doPut"))
    (.parentDoPut this request response)
    (finally
      (NestedContext/pop))))

(defn -doDelete [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doDelete"))
    (.parentDoDelete this request response)
    (finally
      (NestedContext/pop))))

(defn -doHead [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doHead"))
    (.parentDoHead this request response)
    (finally
      (NestedContext/pop))))

(defn -doOptions [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doOptions"))
    (.parentDoOptions this request response)
    (finally
      (NestedContext/pop))))

(defn -doTrace [this request response]
  (try
    (NestedContext/push logContext)
    (.. logger (trace "doTrace"))
    (.parentDoTrace this request response)
    (finally
      (NestedContext/pop))))
