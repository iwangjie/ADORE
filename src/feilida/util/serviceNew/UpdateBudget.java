
package feilida.util.serviceNew;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="buget" type="{http://tempuri.org/}ArrayOfBudgetVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "buget"
})
@XmlRootElement(name = "UpdateBudget")
public class UpdateBudget {

    protected ArrayOfBudgetVO buget;

    /**
     * Gets the value of the buget property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBudgetVO }
     *     
     */
    public ArrayOfBudgetVO getBuget() {
        return buget;
    }

    /**
     * Sets the value of the buget property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBudgetVO }
     *     
     */
    public void setBuget(ArrayOfBudgetVO value) {
        this.buget = value;
    }

}
