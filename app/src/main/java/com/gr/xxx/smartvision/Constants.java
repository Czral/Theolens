package com.gr.xxx.smartvision;

public class Constants {

    /**
     * Lens codes for different types
     */
    public static final int ORMA_TYPE_LENS = 1;
    public static final int EMI_TYPE_LENS = 2;
    public static final int ONE_SIXTY_TYPE_LENS = 3;
    public static final int ONE_SIXTY_SEVEN_TYPE_LENS = 4;
    public static final int ONE_SEVENTY_FOUR_TYPE_LENS = 5;
    public static final int TRANS_BROWN_TYPE_LENS = 6;
    public static final int TRANS_GREY_TYPE_LENS = 7;

    public static final int PC_TYPE_LENS = 41;
    public static final int SIL_TYPE_LENS = 42;

    public static final int SUN_G15_INT = 201;
    public static final int SUN_BROWN_INT = 202;
    public static final int SUN_FIME_INT = 203;

    public static final int SUN_BASE_6 = 306;
    public static final int SUN_BASE_8 = 308;

    public static final int SUN_MONO = 401;
    public static final int SUN_DEGRADE = 402;
    public static final int SUN_POLAR = 403;

    public static final int MIRROR_GOLD = 501;
    public static final int MIRROR_SILVER = 502;
    public static final int MIRROR_BLUE = 503;
    public static final int MIRROR_RED = 504;
    public static final int MIRROR_GREEN = 505;
    public static final int MIRROR_ROSE = 506;
    public static final int MIRROR_YELLOW = 507;
    public static final int MIRROR_ICE = 508;

    public static final int[] SUN_COLORS_INT = {
            SUN_G15_INT,
            SUN_BROWN_INT,
            SUN_FIME_INT
    };

    public static final int[] SUN_BASES_INT = {
            SUN_BASE_6,
            SUN_BASE_8
    };

    public static final int[] SUN_TYPES_INT = {
            SUN_MONO,
            SUN_DEGRADE,
            SUN_POLAR
    };

    public static final int[] MIRRORS = {
            MIRROR_GOLD,
            MIRROR_SILVER,
            MIRROR_BLUE,
            MIRROR_RED,
            MIRROR_GREEN,
            MIRROR_ROSE,
            MIRROR_YELLOW,
            MIRROR_ICE
    };

    /**
     * Lens prices for different types
     */
    public static final double ORMA_PRICE_LENS = 1.00;

    public static final double EMI_PRICE_LENS_FIRST = 2.00;
    public static final double EMI_PRICE_LENS_SECOND = 5.00;

    public static final double ONE_SIXTY_PRICE_LENS_MINUS_FIRST = 6.00;
    public static final double ONE_SIXTY_PRICE_LENS_MINUS_SECOND = 10.00;
    public static final double ONE_SIXTY_PRICE_LENS_PLUS = 9.00;

    public static final double ONE_SIXTY_SEVEN_PRICE_LENS_FIRST_MINUS = 10.00;
    public static final double ONE_SIXTY_SEVEN_PRICE_LENS_SECOND_MINUS = 20.00;
    public static final double ONE_SIXTY_SEVEN_PRICE_LENS_FIRST_PLUS = 12.00;
    public static final double ONE_SIXTY_SEVEN_PRICE_LENS_SECOND_PLUS = 24.00;

    public static final double ONE_SEVENTY_FOUR_PRICE_LENS_FIRST = 20.00;
    public static final double ONE_SEVENTY_FOUR_PRICE_LENS_SECOND = 25.00;

    public static final double TRANS_PRICE_LENS = 7.50;

    public static final double SIL_PRICE_LENS = 8.00;

    public static final double PC_PRICE_LENS = 6.60;

    public static final double SUN_BASE_6_MONO = 1.25;
    public static final double SUN_BASE_8_MONO = 2.00;
    public static final double SUN_BASE_6_DEGRADE = 2.50;

    public static final double SUN_BASE_8_DEGRADE = 4.00;
    public static final double SUN_BASE_6_POLAR = 7.50;
    public static final double SUN_BASE_8_POLAR = 10.00;

    public static final double MIRROR_PRICE = 5.00;

    /**
     * ActionBar titles.
     */
    public static final String PC_SIL = "PC - SIL";
    public static final String ORGANIC = "ORGANIC";
    public static final String SUN_MIRROR = "SUN";
    public static final String ORDER = "ORDER";
    public static final String INFO = "INFO";

    /**
     * Basket titles.
     */
    public static final String ONE_FIFTY_ORMA = "1.50" + "\n" + "ORMA";
    public static final String ONE_FIFTY_SIX = "1.56" + "\n" + "EMI";
    public static final String ONE_SIXTY_ONE = "1.61" + "\n" + "AS";
    public static final String ONE_SIXTY_SEVEN = "1.67" + "\n" + "EMI";
    public static final String ONE_SEVENTY_FOUR = "1.74" + "\n" + "EMI";
    public static final String TRANS_BROWN = "TRANS" + "\n" + "BROWN";
    public static final String TRANS_GREY = "TRANS" + "\n" + "GREY";
    public static final String PC = "PC";
    public static final String SIL = "SIL";
    public static final String SUN = "SUN";
    public static final String MIRROR = "MIRROR";

    public static final String MIRROR_GOLD_TITLE = "MIRROR" + "\n" + "GOLD";
    public static final String MIRROR_SILVER_TITLE = "MIRROR" + "\n" + "SILVER";
    public static final String MIRROR_BLUE_TITLE = "MIRROR" + "\n" + "BLUE";
    public static final String MIRROR_RED_TITLE = "MIRROR" + "\n" + "RED";
    public static final String MIRROR_GREEN_TITLE = "MIRROR" + "\n" + "GREEN";
    public static final String MIRROR_ROSE_TITLE = "MIRROR" + "\n" + "ROSE";
    public static final String MIRROR_YELLOW_TITLE = "MIRROR" + "\n" + "YELLOW";
    public static final String MIRROR_ICE_TITLE = "MIRROR" + "\n" + "ICE";

    public static final String[] MIRRORS_TITLES = {
            MIRROR_GOLD_TITLE,
            MIRROR_SILVER_TITLE,
            MIRROR_BLUE_TITLE,
            MIRROR_RED_TITLE,
            MIRROR_GREEN_TITLE,
            MIRROR_ROSE_TITLE,
            MIRROR_YELLOW_TITLE,
            MIRROR_ICE_TITLE
    };


    public static final String SUN_G15 = "G15";
    public static final String SUN_BROWN = "Brown";
    public static final String SUN_FIME = "Fime";

    public static final String BASE_6 = "Base 6";
    public static final String BASE_8 = "Base 8";

    public static final String MONOCHROMATIC = "Mono";
    public static final String DEGRADE = "Degrade";
    public static final String POLAR = "Polar";

    public static final String[] SUN_BASES_STRING = {
            BASE_6,
            BASE_8
    };

    public static final String[] SUN_TYPES_STRING = {
            MONOCHROMATIC,
            DEGRADE,
            POLAR
    };

    public static final String[] SUN_COLORS_STRING = {
            SUN_G15,
            SUN_BROWN,
            SUN_FIME
    };

    /**
     * Constants for notification.
     */
    public static final String BASKET_NOTIFICATION_ID = "basket notification";
    public static final String IGNORE = "ignore";
    public static final String EMPTY_BASKET = "open activity";


    /**
     * Constants for preferences.
     */
    public static final String SHARED_PREFERENCES = "shared_preferences";
    public static final String FIRST_TIME = "first_time";
    public static final String NAME = "name";

}
