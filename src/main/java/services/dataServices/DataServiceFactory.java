package services.dataServices;

import utilities.Log;

public class DataServiceFactory {
    public static IDataService getDataService(String type) {
        switch (type.trim()) {
            case "oracle":
                return new OracleDataService();

            default:
                Log.error("Does not support data service type: " + type);
                return null;
        }

    }
}
