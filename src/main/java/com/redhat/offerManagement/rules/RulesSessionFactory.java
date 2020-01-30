package com.redhat.offerManagement.rules;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNRuntime;

public class RulesSessionFactory {


    protected static DMNRuntime createDMNRuntime() {
        KieServices kieServices = KieServices.Factory.get();
//        ReleaseId releaseId = kieServices.newReleaseId("com.myspace","OfferPrediction","1.0.0-SNAPSHOT");
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kieContainer.getKieBase()).get(DMNRuntime.class);

        return dmnRuntime;
    }




}
