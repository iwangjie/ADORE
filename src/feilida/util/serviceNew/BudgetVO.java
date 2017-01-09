
package feilida.util.serviceNew;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BudgetVO complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="BudgetVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OADATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="KOSTL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KSTAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GSBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANLKL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EXECTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OPTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OAKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STAFFID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="COMPID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DEPTID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="YYYY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GPKEY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AMOUNT0" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AMOUNT1" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AMOUNT2" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AMOUNT3" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MSGTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MSGTXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BudgetVO", propOrder = {
        "oadate",
        "kostl",
        "kstar",
        "gsber",
        "anlkl",
        "currency",
        "amount",
        "exectype",
        "opttype",
        "oaKey",
        "staffid",
        "compid",
        "deptid",
        "yyyy",
        "mm",
        "gpkey",
        "amount0",
        "amount1",
        "amount2",
        "amount3",
        "msgtyp",
        "msgtxt"
})
public class BudgetVO {

    @XmlElement(name = "OADATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar oadate;
    @XmlElement(name = "KOSTL")
    protected String kostl;
    @XmlElement(name = "KSTAR")
    protected String kstar;
    @XmlElement(name = "GSBER")
    protected String gsber;
    @XmlElement(name = "ANLKL")
    protected String anlkl;
    @XmlElement(name = "CURRENCY")
    protected String currency;
    @XmlElement(name = "AMOUNT", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "EXECTYPE")
    protected String exectype;
    @XmlElement(name = "OPTTYPE")
    protected String opttype;
    @XmlElement(name = "OAKey")
    protected String oaKey;
    @XmlElement(name = "STAFFID")
    protected int staffid;
    @XmlElement(name = "COMPID")
    protected int compid;
    @XmlElement(name = "DEPTID")
    protected int deptid;
    @XmlElement(name = "YYYY")
    protected String yyyy;
    @XmlElement(name = "MM")
    protected String mm;
    @XmlElement(name = "GPKEY")
    protected String gpkey;
    @XmlElement(name = "AMOUNT0", required = true)
    protected BigDecimal amount0;
    @XmlElement(name = "AMOUNT1", required = true)
    protected BigDecimal amount1;
    @XmlElement(name = "AMOUNT2", required = true)
    protected BigDecimal amount2;
    @XmlElement(name = "AMOUNT3", required = true)
    protected BigDecimal amount3;
    @XmlElement(name = "MSGTYP")
    protected String msgtyp;
    @XmlElement(name = "MSGTXT")
    protected String msgtxt;

    /**
     * Gets the value of the oadate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getOADATE() {
        return oadate;
    }

    /**
     * Sets the value of the oadate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setOADATE(XMLGregorianCalendar value) {
        this.oadate = value;
    }

    /**
     * Gets the value of the kostl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKOSTL() {
        return kostl;
    }

    /**
     * Sets the value of the kostl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKOSTL(String value) {
        this.kostl = value;
    }

    /**
     * Gets the value of the kstar property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKSTAR() {
        return kstar;
    }

    /**
     * Sets the value of the kstar property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKSTAR(String value) {
        this.kstar = value;
    }

    /**
     * Gets the value of the gsber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGSBER() {
        return gsber;
    }

    /**
     * Sets the value of the gsber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGSBER(String value) {
        this.gsber = value;
    }

    /**
     * Gets the value of the anlkl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getANLKL() {
        return anlkl;
    }

    /**
     * Sets the value of the anlkl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setANLKL(String value) {
        this.anlkl = value;
    }

    /**
     * Gets the value of the currency property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCURRENCY() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCURRENCY(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the amount property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getAMOUNT() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setAMOUNT(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the exectype property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEXECTYPE() {
        return exectype;
    }

    /**
     * Sets the value of the exectype property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEXECTYPE(String value) {
        this.exectype = value;
    }

    /**
     * Gets the value of the opttype property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOPTTYPE() {
        return opttype;
    }

    /**
     * Sets the value of the opttype property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOPTTYPE(String value) {
        this.opttype = value;
    }

    /**
     * Gets the value of the oaKey property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOAKey() {
        return oaKey;
    }

    /**
     * Sets the value of the oaKey property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOAKey(String value) {
        this.oaKey = value;
    }

    /**
     * Gets the value of the staffid property.
     */
    public int getSTAFFID() {
        return staffid;
    }

    /**
     * Sets the value of the staffid property.
     */
    public void setSTAFFID(int value) {
        this.staffid = value;
    }

    /**
     * Gets the value of the compid property.
     */
    public int getCOMPID() {
        return compid;
    }

    /**
     * Sets the value of the compid property.
     */
    public void setCOMPID(int value) {
        this.compid = value;
    }

    /**
     * Gets the value of the deptid property.
     */
    public int getDEPTID() {
        return deptid;
    }

    /**
     * Sets the value of the deptid property.
     */
    public void setDEPTID(int value) {
        this.deptid = value;
    }

    /**
     * Gets the value of the yyyy property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getYYYY() {
        return yyyy;
    }

    /**
     * Sets the value of the yyyy property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setYYYY(String value) {
        this.yyyy = value;
    }

    /**
     * Gets the value of the mm property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMM() {
        return mm;
    }

    /**
     * Sets the value of the mm property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMM(String value) {
        this.mm = value;
    }

    /**
     * Gets the value of the gpkey property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGPKEY() {
        return gpkey;
    }

    /**
     * Sets the value of the gpkey property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGPKEY(String value) {
        this.gpkey = value;
    }

    /**
     * Gets the value of the amount0 property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getAMOUNT0() {
        return amount0;
    }

    /**
     * Sets the value of the amount0 property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setAMOUNT0(BigDecimal value) {
        this.amount0 = value;
    }

    /**
     * Gets the value of the amount1 property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getAMOUNT1() {
        return amount1;
    }

    /**
     * Sets the value of the amount1 property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setAMOUNT1(BigDecimal value) {
        this.amount1 = value;
    }

    /**
     * Gets the value of the amount2 property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getAMOUNT2() {
        return amount2;
    }

    /**
     * Sets the value of the amount2 property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setAMOUNT2(BigDecimal value) {
        this.amount2 = value;
    }

    /**
     * Gets the value of the amount3 property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getAMOUNT3() {
        return amount3;
    }

    /**
     * Sets the value of the amount3 property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setAMOUNT3(BigDecimal value) {
        this.amount3 = value;
    }

    /**
     * Gets the value of the msgtyp property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMSGTYP() {
        return msgtyp;
    }

    /**
     * Sets the value of the msgtyp property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMSGTYP(String value) {
        this.msgtyp = value;
    }

    /**
     * Gets the value of the msgtxt property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMSGTXT() {
        return msgtxt;
    }

    /**
     * Sets the value of the msgtxt property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMSGTXT(String value) {
        this.msgtxt = value;
    }

}
