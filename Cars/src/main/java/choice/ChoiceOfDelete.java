package choice;

public enum ChoiceOfDelete {
    DELETE_TABLE,
    DELETE_LINE;

    public static ChoiceOfDelete getInteraction(final int interaction) {
        switch (interaction) {
            case 1:
                return DELETE_TABLE;
            case 2:
                return DELETE_LINE;
            default:
                throw new NumberFormatException();
        }
    }
}
