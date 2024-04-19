public class util {
    public static String numbers(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th";
        }

        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }
    public static String timeConverter(long time){
            long milli = time % 100;
            long timeInSeconds = time / 100;
            long seconds = timeInSeconds % 60;
            long timeInMinutes = timeInSeconds / 60;
            long mins = timeInMinutes % 60;
            return mins + ":" + seconds + "." + milli;
        }

        
        
}
