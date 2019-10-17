package com.mapbar.lite.repository;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 13:36
 * @Description:
 */
public class PopulationDistributionRepositoryFactory {

    private static PopulationDistributionRepository repository = new PopulationDistributionRepository();

    public static PopulationDistributionRepository getRepository() {
        return repository;
    }
}
