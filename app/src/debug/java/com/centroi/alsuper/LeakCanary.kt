package com.centroi.alsuper

import leakcanary.LeakCanary

fun initLeakCanary() {
    LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
    LeakCanary.showLeakDisplayActivityLauncherIcon(true)
}