

public class PriceDate implements Comparable<PriceDate> {
    private int date;
    private String month;
    private int year;

    public PriceDate(int date, String monthAbbreviation, int year) {
        this.date = date;
        this.month = monthAbbreviation;
        this.year = year;
    }

    @Override
    public int compareTo(PriceDate other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        } else if (!this.month.equals(other.month)) {
            return this.month.compareTo(other.month);
        } else {
            return Integer.compare(this.date, other.date);
        }
    }

    @Override
    public String toString() {
        return String.format("%02d-%s-%04d", date, month, year); // formats the toString method
    }


}
