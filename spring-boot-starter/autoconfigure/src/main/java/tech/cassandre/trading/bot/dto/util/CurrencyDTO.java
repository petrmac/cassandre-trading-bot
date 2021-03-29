package tech.cassandre.trading.bot.dto.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Currency.
 */
@SuppressWarnings("unused")
public final class CurrencyDTO implements Serializable, Currency {

    /** List of currencies. */
    private static final Map<String, CurrencyDTO> CURRENCIES = new HashMap<>();

    /** Code. */
    private final String code;

    /** Attributes. */
    private final CurrencyDTO.CurrencyAttributes attributes;

    /**
     * Constructor.
     *
     * @param newCode currency code
     */
    CurrencyDTO(final String newCode) {
        this.code = newCode;
        this.attributes = getInstance(newCode).attributes;
    }

    /**
     * Builder.
     *
     * @param builder builder
     */
    CurrencyDTO(final CurrencyDTO.Builder builder) {
        this.code = builder.code;
        this.attributes = getInstance(builder.code).attributes;
    }

    /**
     * For builder.
     */
    protected CurrencyDTO() {
        code = null;
        attributes = null;
    }

    /**
     * Constructor.
     *
     * @param newAlternativeCode alternative code
     * @param newAttributes      attributes.
     */
    private CurrencyDTO(final String newAlternativeCode, final CurrencyDTO.CurrencyAttributes newAttributes) {
        this.code = newAlternativeCode;
        this.attributes = newAttributes;
    }

    /**
     * Gets the set of available currencies.
     *
     * @return available currencies
     */
    public static SortedSet<CurrencyDTO> getAvailableCurrencies() {
        return new TreeSet<>(CURRENCIES.values());
    }

    /**
     * Gets the set of available currency codes.
     *
     * @return currency codes
     */
    public static SortedSet<String> getAvailableCurrencyCodes() {
        return new TreeSet<>(CURRENCIES.keySet());
    }

    /**
     * Returns a Currency instance for the given currency code.
     *
     * @param currencyCode currency code
     * @return currency
     */
    static CurrencyDTO getInstance(final String currencyCode) {
        CurrencyDTO currency = getInstanceNoCreate(currencyCode.toUpperCase());
        return Objects.requireNonNullElseGet(currency, () -> createCurrency(currencyCode.toUpperCase(), null, null));
    }

    /**
     * Returns a Currency instance for the given currency code.
     *
     * @param currencyCode currency code
     * @return currency
     */
    static CurrencyDTO getInstanceNoCreate(final String currencyCode) {
        return CURRENCIES.get(currencyCode.toUpperCase());
    }

    /**
     * Factory.
     *
     * @param commonCode       commonly used code for this currency: "BTC"
     * @param name             Name of the currency: "Bitcoin"
     * @param unicode          Unicode symbol for the currency: "\u20BF" or "฿"
     * @param alternativeCodes Alternative codes for the currency: "XBT"
     * @return currency
     */
    static CurrencyDTO createCurrency(final String commonCode, final String name, final String unicode, final String... alternativeCodes) {
        CurrencyDTO.CurrencyAttributes attributes = new CurrencyDTO.CurrencyAttributes(commonCode, name, unicode, alternativeCodes);
        CurrencyDTO currency = new CurrencyDTO(commonCode, attributes);
        for (String code : attributes.codes) {
            if (commonCode.equals(code)) {
                // common code will always be part of the currencies map
                CURRENCIES.put(code, currency);
            } else if (!CURRENCIES.containsKey(code)) {
                // alternative codes will never overwrite common codes
                CURRENCIES.put(code, new CurrencyDTO(code, attributes));
            }
        }
        return currency;
    }

    /**
     * Get currency code.
     *
     * @return currency code
     */
    public String getCurrencyCode() {
        return code;
    }

    /**
     * Gets the equivalent object with the passed code.
     *
     * <p>This is useful in case some currencies share codes, such that {@link #getInstance(String)}
     * may return the wrong currency.
     *
     * @param newCode The code the returned object will evaluate to
     * @return A Currency representing the same currency but having the passed currency code
     */
    public CurrencyDTO getCodeCurrency(final String newCode) {
        if (newCode.equals(this.code)) {
            return this;
        }
        CurrencyDTO currency = getInstance(newCode);
        if (currency.equals(this)) {
            return currency;
        }
        if (!attributes.codes.contains(newCode)) {
            throw new IllegalArgumentException("Code not listed for this currency");
        }
        return new CurrencyDTO(newCode, attributes);
    }

    /**
     * Gets the equivalent object with an ISO 4217 code, or if none a code which looks ISO compatible (starts with an X), or the constructed currency code if neither exist.
     *
     * @return currency
     */
    public CurrencyDTO getIso4217Currency() {
        if (attributes.isoCode == null) {
            return this;
        }
        // The logic for setting isoCode is in CurrencyAttributes
        return getCodeCurrency(attributes.isoCode);
    }

    /**
     * Gets the equivalent object that was created with the "commonly used" code.
     *
     * @return currency
     */
    public CurrencyDTO getCommonlyUsedCurrency() {
        return getCodeCurrency(attributes.commonCode);
    }

    /**
     * Gets the set of all currency codes associated with this currency.
     *
     * @return currency
     */
    public Set<String> getCurrencyCodes() {
        return attributes.codes;
    }

    /**
     * Gets the unicode symbol of this currency.
     *
     * @return unicode
     */
    public String getSymbol() {
        return attributes.unicode;
    }

    /**
     * Getter for code.
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the name that is suitable for displaying this currency.
     *
     * @return display name
     */
    public String getDisplayName() {
        return attributes.name;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CurrencyDTO other = (CurrencyDTO) obj;
        return attributes.equals(other.attributes);
    }

    /**
     * Currency attributes.
     */
    private static class CurrencyAttributes {

        /** Codes. */
        private final Set<String> codes;

        /** Iso code. */
        private final String isoCode;

        /** common code. */
        private final String commonCode;

        /** Name. */
        private final String name;

        /** Unicode. */
        private final String unicode;

        /**
         * Constructor.
         *
         * @param newCommonCode       common code
         * @param newName             name
         * @param newUnicode          unicode
         * @param newAlternativeCodes alternative codes
         */
        CurrencyAttributes(final String newCommonCode, final String newName, final String newUnicode, final String... newAlternativeCodes) {
            if (newAlternativeCodes.length > 0) {
                this.codes = new TreeSet<>(Arrays.asList(newAlternativeCodes));
                this.codes.add(newCommonCode);
            } else {
                this.codes = Collections.singleton(newCommonCode);
            }

            String possibleIsoProposalCryptoCode = null;

            java.util.Currency javaCurrency = null;
            for (String code : this.codes) {
                if (javaCurrency == null) {
                    try {
                        javaCurrency = java.util.Currency.getInstance(code);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
                if (code.startsWith("X")) {
                    possibleIsoProposalCryptoCode = code;
                }
            }

            if (javaCurrency != null) {
                this.isoCode = javaCurrency.getCurrencyCode();
            } else {
                this.isoCode = possibleIsoProposalCryptoCode;
            }

            this.commonCode = newCommonCode;

            if (newName != null) {
                this.name = newName;
            } else if (javaCurrency != null) {
                this.name = javaCurrency.getDisplayName();
            } else {
                this.name = newCommonCode;
            }

            if (newUnicode != null) {
                this.unicode = newUnicode;
            } else if (javaCurrency != null) {
                this.unicode = javaCurrency.getSymbol();
            } else {
                this.unicode = newCommonCode;
            }
        }

        @Override
        public int hashCode() {
            return commonCode.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            CurrencyDTO.CurrencyAttributes other = (CurrencyDTO.CurrencyAttributes) obj;
            if (commonCode == null) {
                return other.commonCode == null;
            } else {
                return commonCode.equalsIgnoreCase(other.commonCode);
            }
        }
    }

    /**
     * Returns builder.
     *
     * @return builder
     */
    public static CurrencyDTO.Builder builder() {
        return new CurrencyDTO.Builder();
    }

    /**
     * Builder.
     */
    public static final class Builder {

        /** Code. */
        private String code;

        /**
         * Set code.
         *
         * @param newCode code
         * @return builder
         */
        public CurrencyDTO.Builder code(final String newCode) {
            this.code = newCode;
            return this;
        }

        /**
         * Create wallet.
         *
         * @return wallet
         */
        public CurrencyDTO build() {
            return new CurrencyDTO(this);
        }

    }

}
