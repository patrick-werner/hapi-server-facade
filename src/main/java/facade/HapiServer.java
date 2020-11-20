package facade;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import dbService.DbService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet is the actual FHIR server itself
 */
@WebServlet("/*")
public class HapiServer extends RestfulServer {

  private static final Logger LOG = LoggerFactory.getLogger(
      HapiServer.class);
  private static final long serialVersionUID = 1L;

  /**
   * Constructor
   */
  public HapiServer() {
    super(FhirContext.forR4());
  }

  /**
   * This method is called automatically when the servlet is initializing.
   */
  @Override
  public void initialize() {

    DbService dbService = new DbService();

    List<IResourceProvider> providers = new ArrayList<IResourceProvider>();
    providers.add(new RestfulPatientResourceProvider(dbService));
    setResourceProviders(providers);

    /*
     * Use nice coloured HTML when a browser is used to request the content
     */
    registerInterceptor(new ResponseHighlighterInterceptor());
  }
}