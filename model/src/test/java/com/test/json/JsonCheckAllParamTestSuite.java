package com.test.json;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

import org.junitpioneer.jupiter.RepeatFailedTest;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;
import com.test.vendor.mysql.ConcreteMySqlTestSuite;

public class JsonCheckAllParamTestSuite extends ConcreteMySqlTestSuite {
    
    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.addObserver(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initializeQueryString("http://localhost:8080/greeting-json");
        model.getMediatorUtils().getParameterUtil().setListQueryString(Arrays.asList(
            new SimpleEntry<>("name", "{\"c\": 1, \"b\": {\"b\": [1, true, null, {\"a\": {\"a\": \"0'\"}}]}}"),
            new SimpleEntry<>("tenant", "mysql")
        ));
        
        model.getMediatorUtils().getPreferencesUtil().setIsCheckingAllURLParam(true);
        model.getMediatorUtils().getPreferencesUtil().setIsCheckingAllJSONParam(true);
        model.getMediatorUtils().getPreferencesUtil().setIsNotTestingConnection(true);
        model.getMediatorUtils().getConnectionUtil().setMethodInjection(model.getMediatorMethod().getQuery());
        model.getMediatorUtils().getConnectionUtil().setTypeRequest("GET");
        
        model.setIsScanning(true);
        model.getMediatorStrategy().setStrategy(model.getMediatorStrategy().getNormal());
        model.beginInjection();
    }
    
    @Override
    @RepeatFailedTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }
}
