package choice;

public enum ChoiceOfUpdate {
    ADD_LINE,
    CHANGE_LINE;

    public static ChoiceOfUpdate getInteraction(final int interaction) {
        switch (interaction) {
            case 1:
                return ADD_LINE;
            case 2:
                return CHANGE_LINE;
            default:
                throw new NumberFormatException();
        }
    }
}
