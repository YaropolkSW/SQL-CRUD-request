public enum ChoiceOfInteraction {
    INTERACTION_ONE(1),
    INTERACTION_TWO(2);

    private final int interaction;

    ChoiceOfInteraction(final int interaction) {
        this.interaction = interaction;
    }

    public static ChoiceOfInteraction getInteraction(final int interaction) {
        switch (interaction) {
            case 1:
                return INTERACTION_ONE;
            case 2:
                return INTERACTION_TWO;
            default:
                return null;
        }
    }
}
