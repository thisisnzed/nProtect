package com.thisisnzed.nprotect.launcher;

public interface Launch {

    void shutdown();

    void launch(final Launcher launcher, final ClassLoader classLoader);
}