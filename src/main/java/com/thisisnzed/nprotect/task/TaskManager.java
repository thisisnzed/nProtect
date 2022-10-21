package com.thisisnzed.nprotect.task;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.module.ModuleManager;
import com.thisisnzed.nprotect.task.impl.TaskAntiOP;

public class TaskManager {

    private final Protect protect;

    public TaskManager(final Protect protect) {
        this.protect = protect;
    }

    public void startTasks() {
        if (ModuleManager.ANTIOP.isToggle()) {
            if (this.protect.getSettings().getOpPunishment().equals("")) {
                new TaskAntiOP(this.protect, true).runTaskTimerAsynchronously(this.protect.getLauncher(), 60L, (this.protect.getSettings().getCheckOpTimer() * 20L));
            } else
                new TaskAntiOP(this.protect, false).runTaskTimer(this.protect.getLauncher(), 60L, (this.protect.getSettings().getCheckOpTimer() * 20L));
        }
    }
}
