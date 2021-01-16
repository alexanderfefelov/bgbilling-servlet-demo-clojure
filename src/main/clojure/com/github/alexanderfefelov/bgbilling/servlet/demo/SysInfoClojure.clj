(ns com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoClojure
  (:import
    bitel.billing.common.VersionInfo
    java.net.InetAddress
    org.apache.log4j.Logger
    ru.bitel.bgbilling.kernel.module.server.ModuleCache
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

(def logger (Logger/getLogger "SysInfoClojure"))

(def HR "--------------------------------------------------")
(def NL "\n")
(def MB (* 1024 1024))

(defn inspectKernel []
  (def versionInfo (VersionInfo/getVersionInfo "server"))
  (str "0 " (.. versionInfo getModuleName) " " (.. versionInfo getVersionString)))

(defn inspectModule [module]
  (def versionInfo (VersionInfo/getVersionInfo (.. module getName)))
  (str (.. module getId) " " (.. versionInfo getModuleName) " " (.. versionInfo getVersionString)))

(defn collectModules []
  (def modules (.. ModuleCache getInstance getModulesList))
  (str
    (inspectKernel) NL
    (clojure.string/join NL (map (fn [module] (inspectModule module)) modules))))

(defn collectRuntime []
  (str
    "Hostname/IP address: " (.. InetAddress getLocalHost toString) NL
    "Available processors: " (.. Runtime getRuntime availableProcessors) NL
    "Memory free / max / total, MB: "
      (quot (.. Runtime getRuntime freeMemory) MB) " / "
      (quot (.. Runtime getRuntime maxMemory) MB) " / "
      (quot (.. Runtime getRuntime totalMemory) MB)))

(defn collectSysInfo []
  (str
    "Modules" NL HR NL NL (collectModules) NL
    NL
    "Runtime" NL HR NL NL (collectRuntime)))

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

      (.. response getWriter (println (collectSysInfo))))))

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
