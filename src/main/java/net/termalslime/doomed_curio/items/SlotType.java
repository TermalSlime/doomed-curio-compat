package net.termalslime.doomed_curio.items;

public enum SlotType {
    NONE("none"),
    HEAD("hat"),
    EYES("eyes"),
    FACE("balaclava"),
    NECK("neck"),
    TORSO("torso"),
    THORAX("torsofront"),
    BACK("back"),
    ARMS("arms"),
    WRAPS("wraps"),
    HANDS("hands"),
    KNEES("knees"),
    FEET("feet"),
    EARS("ears");

    private final String slotString;

    private SlotType(String description) {
        this.slotString = description;
    }
    public String getSlotString() {return slotString;}
}
