package records;

    public record Prefix(int id, String prefix, int discordServerID) {
        public static final String DEFAULT_PREFIX = "!";
    }
