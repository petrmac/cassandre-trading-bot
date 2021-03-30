package tech.cassandre.trading.bot.dto.util;

public interface Currency {

    String getSymbol();

    /**
     * Getter for code.
     *
     * @return code
     */
    String getCode();

    /**
     * Gets the name that is suitable for displaying this currency.
     *
     * @return display name
     */
    String getDisplayName();

    static Currency forString(final String value) {
        return CurrencyDTO.getInstanceNoCreate(value);
    }


    /** United Arab Emirates Dirham. */
    Currency AED = CurrencyDTO.createCurrency("AED", "United Arab Emirates Dirham", null);

    /** Afghan Afghani. */
    Currency AFN = CurrencyDTO.createCurrency("AFN", "Afghan Afghani", null);

    /** Albanian Lek. */
    Currency ALL = CurrencyDTO.createCurrency("ALL", "Albanian Lek", null);

    /** Armenian Dram. */
    Currency AMD = CurrencyDTO.createCurrency("AMD", "Armenian Dram", null);

    /** Anoncoin. */
    Currency ANC = CurrencyDTO.createCurrency("ANC", "Anoncoin", null);

    /** Netherlands Antillean Guilder. */
    Currency ANG = CurrencyDTO.createCurrency("ANG", "Netherlands Antillean Guilder", null);

    /** Angolan Kwanza. */
    Currency AOA = CurrencyDTO.createCurrency("AOA", "Angolan Kwanza", null);

    /** Aeron. */
    Currency ARN = CurrencyDTO.createCurrency("ARN", "Aeron", null);

    /** Argentine Peso. */
    Currency ARS = CurrencyDTO.createCurrency("ARS", "Argentine Peso", null);

    /** Cosmos. */
    Currency ATOM = CurrencyDTO.createCurrency("ATOM", "Cosmos", null);

    /** Australian Dollar. */
    Currency AUD = CurrencyDTO.createCurrency("AUD", "Australian Dollar", null);

    /** Auroracoin. */
    Currency AUR = CurrencyDTO.createCurrency("AUR", "Auroracoin", null);

    /** Aventus. */
    Currency AVT = CurrencyDTO.createCurrency("AVT", "Aventus", null);

    /** Aruban Florin. */
    Currency AWG = CurrencyDTO.createCurrency("AWG", "Aruban Florin", null);

    /** Azerbaijani Manat. */
    Currency AZN = CurrencyDTO.createCurrency("AZN", "Azerbaijani Manat", null);

    /** Bosnia-Herzegovina Convertible Mark. */
    Currency BAM = CurrencyDTO.createCurrency("BAM", "Bosnia-Herzegovina Convertible Mark", null);

    /** Basic Attention Token. */
    Currency BAT = CurrencyDTO.createCurrency("BAT", "Basic Attention Token", null);

    /** Barbadian Dollar. */
    Currency BBD = CurrencyDTO.createCurrency("BBD", "Barbadian Dollar", null);

    /** BlackCoin. */
    Currency BC = CurrencyDTO.createCurrency("BC", "BlackCoin", null, "BLK");

    /** BitConnect. */
    Currency BCC = CurrencyDTO.createCurrency("BCC", "BitConnect", null);

    /** BitcoinCash. */
    Currency BCH = CurrencyDTO.createCurrency("BCH", "BitcoinCash", null);

    /** BitcoinAtom. */
    Currency BCA = CurrencyDTO.createCurrency("BCA", "BitcoinAtom", null);

    /** BLK. */
    Currency BLK = CurrencyDTO.getInstance("BLK");

    /** Bangladeshi Taka. */
    Currency BDT = CurrencyDTO.createCurrency("BDT", "Bangladeshi Taka", null);

    /** Aten 'Black Gold' Coin. */
    Currency BGC = CurrencyDTO.createCurrency("BGC", "Aten 'Black Gold' Coin", null);

    /** Bulgarian Lev. */
    Currency BGN = CurrencyDTO.createCurrency("BGN", "Bulgarian Lev", null);

    /** Bahraini Dinar. */
    Currency BHD = CurrencyDTO.createCurrency("BHD", "Bahraini Dinar", null);

    /** Bahraini Dinar. */
    Currency BIF = CurrencyDTO.createCurrency("BIF", "Burundian Franc", null);

    /** Bermudan Dollar. */
    Currency BMD = CurrencyDTO.createCurrency("BMD", "Bermudan Dollar", null);

    /** Brunei Dollar. */
    Currency BND = CurrencyDTO.createCurrency("BND", "Brunei Dollar", null);

    /** Bolivian Boliviano. */
    Currency BOB = CurrencyDTO.createCurrency("BOB", "Bolivian Boliviano", null);

    /** Brazilian Real. */
    Currency BRL = CurrencyDTO.createCurrency("BRL", "Brazilian Real", "R$");

    /** Bahamian Dollar. */
    Currency BSD = CurrencyDTO.createCurrency("BSD", "Bahamian Dollar", null);

    /** Bitcoin. */
    Currency BTC = CurrencyDTO.createCurrency("BTC", "Bitcoin", null, "XBT");

    /** Bitcoin Gold. */
    Currency BTG = CurrencyDTO.createCurrency("BTG", "Bitcoin Gold", null);

    /** XBT. */
    Currency XBT = CurrencyDTO.getInstance("XBT");

    /** Bhutanese Ngultrum. */
    Currency BTN = CurrencyDTO.createCurrency("BTN", "Bhutanese Ngultrum", null);

    /** Botswanan Pula. */
    Currency BWP = CurrencyDTO.createCurrency("BWP", "Botswanan Pula", null);

    /** Belarusian Ruble. */
    Currency BYR = CurrencyDTO.createCurrency("BYR", "Belarusian Ruble", null);

    /** Belize Dollar. */
    Currency BZD = CurrencyDTO.createCurrency("BZD", "Belize Dollar", null);

    /** Canadian Dollar. */
    Currency CAD = CurrencyDTO.createCurrency("CAD", "Canadian Dollar", null);

    /** Congolese Franc. */
    Currency CDF = CurrencyDTO.createCurrency("CDF", "Congolese Franc", null);

    /** Swiss Franc. */
    Currency CHF = CurrencyDTO.createCurrency("CHF", "Swiss Franc", null);

    /** Chilean Unit of Account (UF). */
    Currency CLF = CurrencyDTO.createCurrency("CLF", "Chilean Unit of Account (UF)", null);

    /** Chilean Peso. */
    Currency CLP = CurrencyDTO.createCurrency("CLP", "Chilean Peso", null);

    /** Chinacoin. */
    Currency CNC = CurrencyDTO.createCurrency("CNC", "Chinacoin", null);

    /** Chinese Yuan. */
    Currency CNY = CurrencyDTO.createCurrency("CNY", "Chinese Yuan", null);

    /** Colombian Peso. */
    Currency COP = CurrencyDTO.createCurrency("COP", "Colombian Peso", null);

    /** Costa Rican Colón. */
    Currency CRC = CurrencyDTO.createCurrency("CRC", "Costa Rican Colón", null);

    /** Cuban Peso. */
    Currency CUP = CurrencyDTO.createCurrency("CUP", "Cuban Peso", null);

    /** Cape Verdean Escudo. */
    Currency CVE = CurrencyDTO.createCurrency("CVE", "Cape Verdean Escudo", null);

    /** Czech Republic Koruna. */
    Currency CZK = CurrencyDTO.createCurrency("CZK", "Czech Republic Koruna", null);

    /** Dash. */
    Currency DASH = CurrencyDTO.createCurrency("DASH", "Dash", null);

    /** Decred. */
    Currency DCR = CurrencyDTO.createCurrency("DCR", "Decred", null);

    /** DigiByte. */
    Currency DGB = CurrencyDTO.createCurrency("DGB", "DigiByte", null);

    /** Djiboutian Franc. */
    Currency DJF = CurrencyDTO.createCurrency("DJF", "Djiboutian Franc", null);

    /** Danish Krone. */
    Currency DKK = CurrencyDTO.createCurrency("DKK", "Danish Krone", null);

    /** Dogecoin. */
    Currency DOGE = CurrencyDTO.createCurrency("DOGE", "Dogecoin", null, "XDC", "XDG");

    /** XDC. */
    Currency XDC = CurrencyDTO.getInstance("XDC");

    /** XDG. */
    Currency XDG = CurrencyDTO.getInstance("XDG");

    /** Dominican Peso. */
    Currency DOP = CurrencyDTO.createCurrency("DOP", "Dominican Peso", null);

    /** Digitalcoin. */
    Currency DGC = CurrencyDTO.createCurrency("DGC", "Digitalcoin", null);

    /** Devcoin. */
    Currency DVC = CurrencyDTO.createCurrency("DVC", "Devcoin", null);

    /** Darkcoin. */
    Currency DRK = CurrencyDTO.createCurrency("DRK", "Darkcoin", null);

    /** Algerian Dinar. */
    Currency DZD = CurrencyDTO.createCurrency("DZD", "Algerian Dinar", null);

    /** Eidoo. */
    Currency EDO = CurrencyDTO.createCurrency("EDO", "Eidoo", null);

    /** Estonian Kroon. */
    Currency EEK = CurrencyDTO.createCurrency("EEK", "Estonian Kroon", null);

    /** Egoldcoin. */
    Currency EGD = CurrencyDTO.createCurrency("EGD", "Egoldcoin", null);

    /** Egyptian Pound. */
    Currency EGP = CurrencyDTO.createCurrency("EGP", "Egyptian Pound", null);

    /** EOS. */
    Currency EOS = CurrencyDTO.createCurrency("EOS", "EOS", null);

    /** Ethiopian Birr. */
    Currency ETB = CurrencyDTO.createCurrency("ETB", "Ethiopian Birr", null);

    /** Ether Classic. */
    Currency ETC = CurrencyDTO.createCurrency("ETC", "Ether Classic", null);

    /** Ether. */
    Currency ETH = CurrencyDTO.createCurrency("ETH", "Ether", null);

    /** Euro. */
    Currency EUR = CurrencyDTO.createCurrency("EUR", "Euro", null);

    /** Fijian Dollar. */
    Currency FJD = CurrencyDTO.createCurrency("FJD", "Fijian Dollar", null);

    /** First Blood. */
    @SuppressWarnings("checkstyle:ConstantName")
    Currency _1ST = CurrencyDTO.createCurrency("1ST", "First Blood", null);

    /** Falkland Islands Pound. */
    Currency FKP = CurrencyDTO.createCurrency("FKP", "Falkland Islands Pound", null);

    /** Feathercoin. */
    Currency FTC = CurrencyDTO.createCurrency("FTC", "Feathercoin", null);

    /** British Pound Sterling. */
    Currency GBP = CurrencyDTO.createCurrency("GBP", "British Pound Sterling", null);

    /** Georgian Lari. */
    Currency GEL = CurrencyDTO.createCurrency("GEL", "Georgian Lari", null);

    /** Ghanaian Cedi. */
    Currency GHS = CurrencyDTO.createCurrency("GHS", "Ghanaian Cedi", null);

    /** Gigahashes per second. */
    @SuppressWarnings("checkstyle:ConstantName")
    Currency GHs = CurrencyDTO.createCurrency("GHS", "Gigahashes per second", null);

    /** Gibraltar Pound. */
    Currency GIP = CurrencyDTO.createCurrency("GIP", "Gibraltar Pound", null);

    /** Gambian Dalasi. */
    Currency GMD = CurrencyDTO.createCurrency("GMD", "Gambian Dalasi", null);

    /** Guinean Franc. */
    Currency GNF = CurrencyDTO.createCurrency("GNF", "Guinean Franc", null);

    /** Gnosis. */
    Currency GNO = CurrencyDTO.createCurrency("GNO", "Gnosis", null);

    /** Golem. */
    Currency GNT = CurrencyDTO.createCurrency("GNT", "Golem", null);

    /** Guatemalan Quetzal. */
    Currency GTQ = CurrencyDTO.createCurrency("GTQ", "Guatemalan Quetzal", null);

    /** Genesis Vision. */
    Currency GVT = CurrencyDTO.createCurrency("GVT", "Genesis Vision", null);

    /** Guyanaese Dollar. */
    Currency GYD = CurrencyDTO.createCurrency("GYD", "Guyanaese Dollar", null);

    /** Hong Kong Dollar. */
    Currency HKD = CurrencyDTO.createCurrency("HKD", "Hong Kong Dollar", null);

    /** Hive. */
    Currency HVN = CurrencyDTO.createCurrency("HVN", "Hive", null);

    /** Honduran Lempira. */
    Currency HNL = CurrencyDTO.createCurrency("HNL", "Honduran Lempira", null);

    /** Croatian Kuna. */
    Currency HRK = CurrencyDTO.createCurrency("HRK", "Croatian Kuna", null);

    /** Haitian Gourde. */
    Currency HTG = CurrencyDTO.createCurrency("HTG", "Haitian Gourde", null);

    /** Hungarian Forint. */
    Currency HUF = CurrencyDTO.createCurrency("HUF", "Hungarian Forint", null);

    /** Iconomi. */
    Currency ICN = CurrencyDTO.createCurrency("ICN", "Iconomi", null);

    /** Indonesian Rupiah. */
    Currency IDR = CurrencyDTO.createCurrency("IDR", "Indonesian Rupiah", null);

    /** Israeli New Sheqel. */
    Currency ILS = CurrencyDTO.createCurrency("ILS", "Israeli New Sheqel", null);

    /** Indian Rupee. */
    Currency INR = CurrencyDTO.createCurrency("INR", "Indian Rupee", null);

    /** I/OCoin. */
    Currency IOC = CurrencyDTO.createCurrency("IOC", "I/OCoin", null);

    /** IOTA. */
    Currency IOT = CurrencyDTO.createCurrency("IOT", "IOTA", null);

    /** Iraqi Dinar. */
    Currency IQD = CurrencyDTO.createCurrency("IQD", "Iraqi Dinar", null);

    /** Iranian Rial. */
    Currency IRR = CurrencyDTO.createCurrency("IRR", "Iranian Rial", null);

    /** Icelandic Króna. */
    Currency ISK = CurrencyDTO.createCurrency("ISK", "Icelandic Króna", null);

    /** iXcoin. */
    Currency IXC = CurrencyDTO.createCurrency("IXC", "iXcoin", null);

    /** Jersey Pound. */
    Currency JEP = CurrencyDTO.createCurrency("JEP", "Jersey Pound", null);

    /** Jamaican Dollar. */
    Currency JMD = CurrencyDTO.createCurrency("JMD", "Jamaican Dollar", null);

    /** Jordanian Dinar. */
    Currency JOD = CurrencyDTO.createCurrency("JOD", "Jordanian Dinar", null);

    /** Japanese Yen. */
    Currency JPY = CurrencyDTO.createCurrency("JPY", "Japanese Yen", null);

    /** KuCoin Shares. */
    Currency KCS = CurrencyDTO.createCurrency("KCS", "KuCoin Shares", null);

    /** Kenyan Shilling. */
    Currency KES = CurrencyDTO.createCurrency("KES", "Kenyan Shilling", null);

    /** Kyrgystani Som. */
    Currency KGS = CurrencyDTO.createCurrency("KGS", "Kyrgystani Som", null);

    /** Cambodian Riel. */
    Currency KHR = CurrencyDTO.createCurrency("KHR", "Cambodian Riel", null);

    /** KickCoin. */
    Currency KICK = CurrencyDTO.createCurrency("KICK", "KickCoin", null);

    /** Comorian Franc. */
    Currency KMF = CurrencyDTO.createCurrency("KMF", "Comorian Franc", null);

    /** North Korean Won. */
    Currency KPW = CurrencyDTO.createCurrency("KPW", "North Korean Won", null);

    /** South Korean Won. */
    Currency KRW = CurrencyDTO.createCurrency("KRW", "South Korean Won", null);

    /** Kuwaiti Dinar. */
    Currency KWD = CurrencyDTO.createCurrency("KWD", "Kuwaiti Dinar", null);

    /** Cayman Islands Dollar. */
    Currency KYD = CurrencyDTO.createCurrency("KYD", "Cayman Islands Dollar", null);

    /** Kazakhstani Tenge. */
    Currency KZT = CurrencyDTO.createCurrency("KZT", "Kazakhstani Tenge", null);

    /** Laotian Kip. */
    Currency LAK = CurrencyDTO.createCurrency("LAK", "Laotian Kip", null);

    /** Lebanese Pound. */
    Currency LBP = CurrencyDTO.createCurrency("LBP", "Lebanese Pound", null);

    /** Lebanese Pound. */
    Currency LSK = CurrencyDTO.createCurrency("LSK", "Lisk", null);

    /** Sri Lankan Rupee. */
    Currency LKR = CurrencyDTO.createCurrency("LKR", "Sri Lankan Rupee", null);

    /** Liberian Dolla. */
    Currency LRD = CurrencyDTO.createCurrency("LRD", "Liberian Dollar", null);

    /** Lesotho Loti. */
    Currency LSL = CurrencyDTO.createCurrency("LSL", "Lesotho Loti", null);

    /** Litecoin. */
    Currency LTC = CurrencyDTO.createCurrency("LTC", "Litecoin", null, "XLT");

    /** XLT. */
    Currency XLT = CurrencyDTO.getInstance("XLT");

    /** Lithuanian Litas. */
    Currency LTL = CurrencyDTO.createCurrency("LTL", "Lithuanian Litas", null);

    /** Latvian Lats. */
    Currency LVL = CurrencyDTO.createCurrency("LVL", "Latvian Lats", null);

    /** Libyan Dinar. */
    Currency LYD = CurrencyDTO.createCurrency("LYD", "Libyan Dinar", null);

    /** Moroccan Dirham. */
    Currency MAD = CurrencyDTO.createCurrency("MAD", "Moroccan Dirham", null);

    /** Moldovan Leu. */
    Currency MDL = CurrencyDTO.createCurrency("MDL", "Moldovan Leu", null);

    /** MegaCoin. */
    Currency MEC = CurrencyDTO.createCurrency("MEC", "MegaCoin", null);

    /** Malagasy Ariary. */
    Currency MGA = CurrencyDTO.createCurrency("MGA", "Malagasy Ariary", null);

    /** Macedonian Denar. */
    Currency MKD = CurrencyDTO.createCurrency("MKD", "Macedonian Denar", null);

    /** Melonport. */
    Currency MLN = CurrencyDTO.createCurrency("MLN", "Melonport", null);

    /** Myanma Kyat. */
    Currency MMK = CurrencyDTO.createCurrency("MMK", "Myanma Kyat", null);

    /** Mongolian Tugrik. */
    Currency MNT = CurrencyDTO.createCurrency("MNT", "Mongolian Tugrik", null);

    /** Macanese Pataca. */
    Currency MOP = CurrencyDTO.createCurrency("MOP", "Macanese Pataca", null);

    /** Mauritanian Ouguiya. */
    Currency MRO = CurrencyDTO.createCurrency("MRO", "Mauritanian Ouguiya", null);

    /** Mason Coin. */
    Currency MSC = CurrencyDTO.createCurrency("MSC", "Mason Coin", null);

    /** Mauritian Rupee. */
    Currency MUR = CurrencyDTO.createCurrency("MUR", "Mauritian Rupee", null);

    /** Maldivian Rufiyaa. */
    Currency MVR = CurrencyDTO.createCurrency("MVR", "Maldivian Rufiyaa", null);

    /** Malawian Kwacha. */
    Currency MWK = CurrencyDTO.createCurrency("MWK", "Malawian Kwacha", null);

    /** Mexican Peso. */
    Currency MXN = CurrencyDTO.createCurrency("MXN", "Mexican Peso", null);

    /** Malaysian Ringgit. */
    Currency MYR = CurrencyDTO.createCurrency("MYR", "Malaysian Ringgit", null);

    /** Mozambican Metical. */
    Currency MZN = CurrencyDTO.createCurrency("MZN", "Mozambican Metical", null);

    /** Namibian Dollar. */
    Currency NAD = CurrencyDTO.createCurrency("NAD", "Namibian Dollar", null);

    /** No BS Crypto. */
    Currency NOBS = CurrencyDTO.createCurrency("NOBS", "No BS Crypto", null);

    /** NEO. */
    Currency NEO = CurrencyDTO.createCurrency("NEO", "NEO", null);

    /** Nigerian Naira. */
    Currency NGN = CurrencyDTO.createCurrency("NGN", "Nigerian Naira", null);

    /** Nicaraguan Córdoba. */
    Currency NIO = CurrencyDTO.createCurrency("NIO", "Nicaraguan Córdoba", null);

    /** Namecoin. */
    Currency NMC = CurrencyDTO.createCurrency("NMC", "Namecoin", null);

    /** Norwegian Krone. */
    Currency NOK = CurrencyDTO.createCurrency("NOK", "Norwegian Krone", null);

    /** Nepalese Rupee. */
    Currency NPR = CurrencyDTO.createCurrency("NPR", "Nepalese Rupee", null);

    /** Novacoin. */
    Currency NVC = CurrencyDTO.createCurrency("NVC", "Novacoin", null);

    /** Nextcoin. */
    Currency NXT = CurrencyDTO.createCurrency("NXT", "Nextcoin", null);

    /** New Zealand Dollar. */
    Currency NZD = CurrencyDTO.createCurrency("NZD", "New Zealand Dollar", null);

    /** OmiseGO. */
    Currency OMG = CurrencyDTO.createCurrency("OMG", "OmiseGO", null);

    /** Omani Rial. */
    Currency OMR = CurrencyDTO.createCurrency("OMR", "Omani Rial", null);

    /** Panamanian Balboa. */
    Currency PAB = CurrencyDTO.createCurrency("PAB", "Panamanian Balboa", null);

    /** Peruvian Nuevo Sol. */
    Currency PEN = CurrencyDTO.createCurrency("PEN", "Peruvian Nuevo Sol", null);

    /** Papua New Guinean Kina. */
    Currency PGK = CurrencyDTO.createCurrency("PGK", "Papua New Guinean Kina", null);

    /** Philippine Peso. */
    Currency PHP = CurrencyDTO.createCurrency("PHP", "Philippine Peso", null);

    /** Pakistani Rupee. */
    Currency PKR = CurrencyDTO.createCurrency("PKR", "Pakistani Rupee", null);

    /** Polish Zloty. */
    Currency PLN = CurrencyDTO.createCurrency("PLN", "Polish Zloty", null);

    /** PotCoin. */
    Currency POT = CurrencyDTO.createCurrency("POT", "PotCoin", null);

    /** Peercoin. */
    Currency PPC = CurrencyDTO.createCurrency("PPC", "Peercoin", null);

    /** Paraguayan Guarani. */
    Currency PYG = CurrencyDTO.createCurrency("PYG", "Paraguayan Guarani", null);

    /** Qatari Rial. */
    Currency QAR = CurrencyDTO.createCurrency("QAR", "Qatari Rial", null);

    /** QuarkCoin. */
    Currency QRK = CurrencyDTO.createCurrency("QRK", "QuarkCoin", null);

    /** Qtum. */
    Currency QTUM = CurrencyDTO.createCurrency("QTUM", "Qtum", null);

    /** Augur. */
    Currency REP = CurrencyDTO.createCurrency("REP", "Augur", null);

    /** Romanian Leu. */
    Currency RON = CurrencyDTO.createCurrency("RON", "Romanian Leu", null);

    /** Serbian Dinar. */
    Currency RSD = CurrencyDTO.createCurrency("RSD", "Serbian Dinar", null);

    /** Russian Ruble. */
    Currency RUB = CurrencyDTO.createCurrency("RUB", "Russian Ruble", null);

    /** Old Russian Ruble. */
    Currency RUR = CurrencyDTO.createCurrency("RUR", "Old Russian Ruble", null);

    /** Rwandan Franc. */
    Currency RWF = CurrencyDTO.createCurrency("RWF", "Rwandan Franc", null);

    /** Saudi Riyal. */
    Currency SAR = CurrencyDTO.createCurrency("SAR", "Saudi Riyal", null);

    /** Stablecoin. */
    Currency SBC = CurrencyDTO.createCurrency("SBC", "Stablecoin", null);

    /** Solomon Islands Dollar. */
    Currency SBD = CurrencyDTO.createCurrency("SBD", "Solomon Islands Dollar", null);

    /** Siacoin. */
    Currency SC = CurrencyDTO.createCurrency("SC", "Siacoin", null);

    /** Seychellois Rupee. */
    Currency SCR = CurrencyDTO.createCurrency("SCR", "Seychellois Rupee", null);

    /** Sudanese Pound. */
    Currency SDG = CurrencyDTO.createCurrency("SDG", "Sudanese Pound", null);

    /** Swedish Krona. */
    Currency SEK = CurrencyDTO.createCurrency("SEK", "Swedish Krona", null);

    /** Singapore Dollar. */
    Currency SGD = CurrencyDTO.createCurrency("SGD", "Singapore Dollar", null);

    /** Saint Helena Pound. */
    Currency SHP = CurrencyDTO.createCurrency("SHP", "Saint Helena Pound", null);

    /** Sierra Leonean Leone. */
    Currency SLL = CurrencyDTO.createCurrency("SLL", "Sierra Leonean Leone", null);

    /** SmartCash. */
    Currency SMART = CurrencyDTO.createCurrency("SMART", "SmartCash", null);

    /** Somali Shilling. */
    Currency SOS = CurrencyDTO.createCurrency("SOS", "Somali Shilling", null);

    /** Surinamese Dollar. */
    Currency SRD = CurrencyDTO.createCurrency("SRD", "Surinamese Dollar", null);

    /** Startcoin. */
    Currency START = CurrencyDTO.createCurrency("START", "startcoin", null);

    /** Steem. */
    Currency STEEM = CurrencyDTO.createCurrency("STEEM", "Steem", null);

    /** São Tomé and Príncipe Dobra. */
    Currency STD = CurrencyDTO.createCurrency("STD", "São Tomé and Príncipe Dobra", null);

    /** Stellar. */
    Currency STR = CurrencyDTO.createCurrency("STR", "Stellar", null);

    /** Stratis. */
    Currency STRAT = CurrencyDTO.createCurrency("STRAT", "Stratis", null);

    /** Salvadoran Colón. */
    Currency SVC = CurrencyDTO.createCurrency("SVC", "Salvadoran Colón", null);

    /** Syrian Pound. */
    Currency SYP = CurrencyDTO.createCurrency("SYP", "Syrian Pound", null);

    /** Swazi Lilangeni. */
    Currency SZL = CurrencyDTO.createCurrency("SZL", "Swazi Lilangeni", null);

    /** Thai Baht. */
    Currency THB = CurrencyDTO.createCurrency("THB", "Thai Baht", null);

    /** Tajikistani Somoni. */
    Currency TJS = CurrencyDTO.createCurrency("TJS", "Tajikistani Somoni", null);

    /** Turkmenistani Manat. */
    Currency TMT = CurrencyDTO.createCurrency("TMT", "Turkmenistani Manat", null);

    /** Tunisian Dinar. */
    Currency TND = CurrencyDTO.createCurrency("TND", "Tunisian Dinar", null);

    /** Tongan Paʻanga. */
    Currency TOP = CurrencyDTO.createCurrency("TOP", "Tongan Paʻanga", null);

    /** Terracoin. */
    Currency TRC = CurrencyDTO.createCurrency("TRC", "Terracoin", null);

    /** Turkish Lira",. */
    Currency TRY = CurrencyDTO.createCurrency("TRY", "Turkish Lira", null);

    /** Trinidad and Tobago Dollar. */
    Currency TTD = CurrencyDTO.createCurrency("TTD", "Trinidad and Tobago Dollar", null);

    /** New Taiwan Dollar. */
    Currency TWD = CurrencyDTO.createCurrency("TWD", "New Taiwan Dollar", null);

    /** Tanzanian Shilling. */
    Currency TZS = CurrencyDTO.createCurrency("TZS", "Tanzanian Shilling", null);

    /** Ukrainian Hryvnia. */
    Currency UAH = CurrencyDTO.createCurrency("UAH", "Ukrainian Hryvnia", null);

    /** Ugandan Shilling. */
    Currency UGX = CurrencyDTO.createCurrency("UGX", "Ugandan Shilling", null);

    /** United States Dollar. */
    Currency USD = CurrencyDTO.createCurrency("USD", "United States Dollar", "$");

    /** Tether USD Anchor. */
    Currency USDT = CurrencyDTO.createCurrency("USDT", "Tether USD Anchor", null);

    /** Unitary Status Dollar eCoin. */
    Currency USDE = CurrencyDTO.createCurrency("USDE", "Unitary Status Dollar eCoin", null);

    /** Ultracoin. */
    Currency UTC = CurrencyDTO.createCurrency("UTC", "Ultracoin", null);

    /** Uruguayan Peso. */
    Currency UYU = CurrencyDTO.createCurrency("UYU", "Uruguayan Peso", null);

    /** Uzbekistan Som. */
    Currency UZS = CurrencyDTO.createCurrency("UZS", "Uzbekistan Som", null);

    /** Venezuelan Bolívar. */
    Currency VEF = CurrencyDTO.createCurrency("VEF", "Venezuelan Bolívar", null);

    /** Hub Culture's Vet. */
    Currency VET = CurrencyDTO.createCurrency("VET", "Hub Culture's Vet", null, "VEN");

    /** Hub Culture's Ven. */
    Currency VEN = CurrencyDTO.createCurrency("VEN", "Hub Culture's Ven", null, "XVN");

    /** Tezos. */
    Currency XTZ = CurrencyDTO.createCurrency("XTZ", "Tezos", null);

    /** XVN. */
    Currency XVN = CurrencyDTO.getInstance("XVN");

    /** Viberate. */
    Currency VIB = CurrencyDTO.createCurrency("VIB", "Viberate", null);

    /** Vietnamese Dong. */
    Currency VND = CurrencyDTO.createCurrency("VND", "Vietnamese Dong", null);

    /** Vanuatu Vatu. */
    Currency VUV = CurrencyDTO.createCurrency("VUV", "Vanuatu Vatu", null);

    /** WorldCoin. */
    Currency WDC = CurrencyDTO.createCurrency("WDC", "WorldCoin", null);

    /** Samoan Tala. */
    Currency WST = CurrencyDTO.createCurrency("WST", "Samoan Tala", null);

    /** CFA Franc BEAC. */
    Currency XAF = CurrencyDTO.createCurrency("XAF", "CFA Franc BEAC", null);

    /** Asch. */
    Currency XAS = CurrencyDTO.createCurrency("XAS", "Asch", null);

    /** Xaurum. */
    Currency XAUR = CurrencyDTO.createCurrency("XAUR", "Xaurum", null);

    /** East Caribbean Dollar. */
    Currency XCD = CurrencyDTO.createCurrency("XCD", "East Caribbean Dollar", null);

    /** Special Drawing Rights. */
    Currency XDR = CurrencyDTO.createCurrency("XDR", "Special Drawing Rights", null);

    /** NEM. */
    Currency XEM = CurrencyDTO.createCurrency("XEM", "NEM", null);

    /** Stellar Lumen. */
    Currency XLM = CurrencyDTO.createCurrency("XLM", "Stellar Lumen", null);

    /** Monero. */
    Currency XMR = CurrencyDTO.createCurrency("XMR", "Monero", null);

    /** Rai Blocks. */
    Currency XRB = CurrencyDTO.createCurrency("XRB", "Rai Blocks", null);

    /** CFA Franc BCEAO. */
    Currency XOF = CurrencyDTO.createCurrency("XOF", "CFA Franc BCEAO", null);

    /** CFP Franc. */
    Currency XPF = CurrencyDTO.createCurrency("XPF", "CFP Franc", null);

    /** Primecoin. */
    Currency XPM = CurrencyDTO.createCurrency("XPM", "Primecoin", null);

    /** Ripple. */
    Currency XRP = CurrencyDTO.createCurrency("XRP", "Ripple", null);

    /** YbCoin. */
    Currency YBC = CurrencyDTO.createCurrency("YBC", "YbCoin", null);

    /** Yemeni Rial. */
    Currency YER = CurrencyDTO.createCurrency("YER", "Yemeni Rial", null);

    /** South African Rand. */
    Currency ZAR = CurrencyDTO.createCurrency("ZAR", "South African Rand", null);

    /** Zcash. */
    Currency ZEC = CurrencyDTO.createCurrency("ZEC", "Zcash", null);

    /** ZenCash. */
    Currency ZEN = CurrencyDTO.createCurrency("ZEN", "ZenCash", null);

    /** Zambian Kwacha. */
    Currency ZMK = CurrencyDTO.createCurrency("ZMK", "Zambian Kwacha", null);

    /** ziftrCOIN. */
    Currency ZRC = CurrencyDTO.createCurrency("ZRC", "ziftrCOIN", null);

    /** Zimbabwean Dollar. */
    Currency ZWL = CurrencyDTO.createCurrency("ZWL", "Zimbabwean Dollar", null);

    /** March 30th. */
    Currency H18 = CurrencyDTO.createCurrency("H18", "March 30th", null);

    /** June 29th. */
    Currency M18 = CurrencyDTO.createCurrency("M18", "June 29th", null);

    /** September 28th. */
    Currency U18 = CurrencyDTO.createCurrency("U18", "September 28th", null);

    /** December 28th. */
    Currency Z18 = CurrencyDTO.createCurrency("Z18", "December 28th", null);

    /** March 29th. */
    Currency H19 = CurrencyDTO.createCurrency("H19", "March 29th", null);

    /** June 28th. */
    Currency M19 = CurrencyDTO.createCurrency("M19", "June 28th", null);

    /** Bankera Coin. */
    Currency BNK = CurrencyDTO.createCurrency("BNK", "Bankera Coin", null);

    /** Binance Coin. */
    Currency BNB = CurrencyDTO.createCurrency("BNB", "Binance Coin", null);

    /** Quantstamp. */
    Currency QSP = CurrencyDTO.createCurrency("QSP", "Quantstamp", null);

    /** Iota. */
    Currency IOTA = CurrencyDTO.createCurrency("IOTA", "Iota", null);

    /** Yoyow. */
    Currency YOYO = CurrencyDTO.createCurrency("YOYO", "Yoyow", null);

    /** Bitshare. */
    Currency BTS = CurrencyDTO.createCurrency("BTS", "Bitshare", null);

    /** Icon. */
    Currency ICX = CurrencyDTO.createCurrency("ICX", "Icon", null);

    /** Monaco. */
    Currency MCO = CurrencyDTO.createCurrency("MCO", "Monaco", null);

    /** Cindicator. */
    Currency CND = CurrencyDTO.createCurrency("CND", "Cindicator", null);

    /** Verge. */
    Currency XVG = CurrencyDTO.createCurrency("XVG", "Verge", null);

    /** Po.et. */
    Currency POE = CurrencyDTO.createCurrency("POE", "Po.et", null);

    /** Tron. */
    Currency TRX = CurrencyDTO.createCurrency("TRX", "Tron", null);

    /** Cardano. */
    Currency ADA = CurrencyDTO.createCurrency("ADA", "Cardano", null);

    /** FunFair. */
    Currency FUN = CurrencyDTO.createCurrency("FUN", "FunFair", null);

    /** Hshare. */
    Currency HSR = CurrencyDTO.createCurrency("HSR", "Hshare", null);

    /** ETHLend. */
    Currency LEND = CurrencyDTO.createCurrency("LEND", "ETHLend", null);

    /** aelf. */
    Currency ELF = CurrencyDTO.createCurrency("ELF", "aelf", null);

    /** Storj. */
    Currency STORJ = CurrencyDTO.createCurrency("STORJ", "Storj", null);

    /** Modum. */
    Currency MOD = CurrencyDTO.createCurrency("MOD", "Modum", null);

}
