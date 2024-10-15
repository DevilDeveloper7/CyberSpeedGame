package ru.devildeveloper74.model;

import java.util.List;
import java.util.Objects;

public class WinCombination {
    private String name;
    private Double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    private List<List<String>> coveredAreas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(Double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }

    public void setCoveredAreas(List<List<String>> coveredAreas) {
        this.coveredAreas = coveredAreas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinCombination that = (WinCombination) o;
        return Objects.equals(name, that.name) && Objects.equals(rewardMultiplier, that.rewardMultiplier) && Objects.equals(when, that.when) && Objects.equals(count, that.count) && Objects.equals(group, that.group) && Objects.equals(coveredAreas, that.coveredAreas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rewardMultiplier, when, count, group, coveredAreas);
    }
}
