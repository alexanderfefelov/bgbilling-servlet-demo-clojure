(ns com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilterClojure
  (:import
    bitel.billing.server.Server
    org.apache.log4j.Logger
    ru.bitel.bgbilling.server.util.ServerUtils
    ru.bitel.common.logging.NestedContext)
  (:gen-class
    :implements [javax.servlet.Filter]))

(def logContext "servlet")

(def logger (Logger/getLogger "UptimePuncherFilterClojure"))

(defmacro wrap [block] `
  (try
    (do
      (NestedContext/push logContext)
      (~@block))
    (finally
      (NestedContext/pop))))

(defn -init [this filterConfig]
  (wrap
    (.. logger (trace "init"))))

(defn -destroy [this]
  (wrap
    (.. logger (trace "destroy"))))

(defn -doFilter [this request response chain]
  (wrap
    (do
      (.. logger (trace "doFilter"))

      (.. chain (doFilter request response))
      (.. response (addHeader "X-BGBilling-Server-Uptime" (ServerUtils/uptimeStatus Server/START_TIME))))))
