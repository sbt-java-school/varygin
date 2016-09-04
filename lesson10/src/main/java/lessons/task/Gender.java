package lessons.task;

/**
 * Gender type
 */
public enum Gender {
    MALE ('M'),
    FEMALE ('F');

    Gender(Character shortName) {
        this.shortName = shortName;
    }

    private Character shortName;

    public Character getShortName() {
        return shortName;
    }

    public static Gender getByShort(Character value) {
        return (value == 'M') ? MALE : FEMALE;
    }

    @Override
    public String toString() {
        return shortName + "";
    }
}
