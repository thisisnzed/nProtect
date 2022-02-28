package com.nz1337.nprotect.manager;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.tasks.TaskAntiOP;

public class TaskManager {

    private final Protect protect;

    public TaskManager(final Protect protect) {
        this.protect = protect;
        this.startTasks();
    }

    private void startTasks() {
        if (ModuleManager.ANTIOP.isToggle()) {
            if (this.protect.getSettings().getOpPunishment().equals("")) {
                new TaskAntiOP(this.protect, true).runTaskTimerAsynchronously(this.protect.getLauncher(), 60L, (this.protect.getSettings().getCheckOpTimer() * 20L));
            } else new TaskAntiOP(this.protect, false).runTaskTimer(this.protect.getLauncher(), 60L, (this.protect.getSettings().getCheckOpTimer() * 20L));
        }
    }
}
