package com.nz1337.nprotect.launcher;

public interface Launch {

    void shutdown();

    void launch(Launcher launcher, ClassLoader classLoader);
}