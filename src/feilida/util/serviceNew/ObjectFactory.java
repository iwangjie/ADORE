
package feilida.util.serviceNew;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the feilida.util.serviceNew package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: feilida.util.serviceNew
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BudgetVO }
     * 
     */
    public BudgetVO createBudgetVO() {
        return new BudgetVO();
    }

    /**
     * Create an instance of {@link QueryBudgetResponse }
     * 
     */
    public QueryBudgetResponse createQueryBudgetResponse() {
        return new QueryBudgetResponse();
    }

    /**
     * Create an instance of {@link QueryBudget }
     * 
     */
    public QueryBudget createQueryBudget() {
        return new QueryBudget();
    }

    /**
     * Create an instance of {@link ArrayOfBudgetVO }
     * 
     */
    public ArrayOfBudgetVO createArrayOfBudgetVO() {
        return new ArrayOfBudgetVO();
    }

    /**
     * Create an instance of {@link UpdateBudget }
     * 
     */
    public UpdateBudget createUpdateBudget() {
        return new UpdateBudget();
    }

    /**
     * Create an instance of {@link UpdateBudgetResponse }
     * 
     */
    public UpdateBudgetResponse createUpdateBudgetResponse() {
        return new UpdateBudgetResponse();
    }

}
