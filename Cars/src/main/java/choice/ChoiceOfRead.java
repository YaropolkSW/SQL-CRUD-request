package choice;

public enum ChoiceOfRead {
    READ_ONE_LINE,
    READ_ALL_LINES;

    public static ChoiceOfRead getInteraction(final int interaction) {
        switch (interaction) {
            case 1:
                return READ_ONE_LINE;
            case 2:
                return READ_ALL_LINES;
            default:
                throw new NumberFormatException();
        }
    }
}
