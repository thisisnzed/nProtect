package com.nz1337.nprotect.launcher;

public interface Launch {

    void shutdown();

    void launch(final Launcher launcher, final ClassLoader classLoader);
}