package com.vexbot.vexmine.mine;

import java.util.UUID;

public final class MiningTask {

    private final UUID id;
    private final MiningRegion region;
    private final String mode;
    private final String ownerName;
    private final long createdAt;
    private final int botCount;
    private int minedBlocks;
    private boolean active;

    public MiningTask(UUID id, MiningRegion region, String mode, String ownerName) {
        this.id = id;
        this.region = region;
        this.mode = mode;
        this.ownerName = ownerName;
        this.createdAt = System.currentTimeMillis();
        this.botCount = "team".equalsIgnoreCase(mode) ? 3 : 1;
        this.active = true;
    }

    public UUID getId() {
        return id;
    }

    public MiningRegion getRegion() {
        return region;
    }

    public String getMode() {
        return mode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getBotCount() {
        return botCount;
    }

    public int getMinedBlocks() {
        return minedBlocks;
    }

    public void incrementMinedBlocks() {
        this.minedBlocks++;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
