package org.cr.rheos.repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();
    public static void setCurrentTenant(String tenant) {
        log.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
    public static void clear() {
        currentTenant.set(null);
    }
}