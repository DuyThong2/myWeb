/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.plan;

import Utility.JDBCUtil;
import dto.plan.DayPlan;
import dto.plan.MealPlan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class MealPlanDAO {

    public ArrayList<MealPlan> getAllMealPlans() {
        ArrayList<MealPlan> list = new ArrayList<>();
        String getAllMealPlanSql = "SELECT [id],[name],[type],[imgURL],[content],[status] from [dbo].[MealPlan] order by status desc";
        String getDayPlanSql = "SELECT [id],[dayInWeek],[status],[MealId],[MealPlanId],[CustomerPlanId] from [dbo].[DayPlan]\n"
                + "where MealPlanId = ?";
        try (Connection conn = JDBCUtil.getConnection();
                Statement statement = conn.createStatement();
                PreparedStatement pst = conn.prepareStatement(getDayPlanSql);) {

            ResultSet mealPlanRst = statement.executeQuery(getAllMealPlanSql);
            while (mealPlanRst.next()) {
                String mealPlanId = mealPlanRst.getString("id");
                String name = mealPlanRst.getString("name");
                String type = mealPlanRst.getString("type");
                String imgURL = mealPlanRst.getString("imgURL");
                String content = mealPlanRst.getString("content");
                int mealPlanStatus = mealPlanRst.getInt("status");
                List<DayPlan> dayPlanList = new ArrayList<>();
                pst.setString(1, mealPlanId);
                ResultSet dayPlanRst = pst.executeQuery();
                while (dayPlanRst.next()) {
                    String dayPlanId = dayPlanRst.getString("id");
                    int dayInWeek = dayPlanRst.getInt("dayInWeek");
                    int dayPlanStatus = dayPlanRst.getInt("status");
                    String mealId = dayPlanRst.getString("MealId");
//                    String id, String mealId, String mealPlanId, String customerPlanId, int dayInWeek, int status
                    DayPlan dayPlan = new DayPlan(dayPlanId, mealId, mealPlanId, null, dayInWeek, dayPlanStatus);
                    dayPlanList.add(dayPlan);
                }
                //   public MealPlan(String id, String name, String type, String content, String imgUrl, int status, List<DayPlan> dayPlanContains) {

                MealPlan mealPlan = new MealPlan(mealPlanId, name, type, content, imgURL, mealPlanStatus, dayPlanList);
                list.add(mealPlan);
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<MealPlan> getAllMeanLanByName(String searchName) {
        ArrayList<MealPlan> list = new ArrayList<>();
        String getDayPlanSql = "SELECT [id],[dayInWeek],[status],[MealId],[MealPlanId],[CustomerPlanId] from [dbo].[DayPlan]\n"
                + "where MealPlanId = ? ";
        String getMealPlanByNameSql = "select [id],[type],[imgURL],[content],[status],[name] from [dbo].[MealPlan]\n"
                + "  where [name] like ? order by status desc";
        try (Connection cn = JDBCUtil.getConnection();
                PreparedStatement mealPlanPst = cn.prepareStatement(getMealPlanByNameSql);
                PreparedStatement dayPlanPst = cn.prepareStatement(getMealPlanByNameSql);) {
            mealPlanPst.setString(1, "%" + searchName + "%");
            ResultSet mealPlanTable = mealPlanPst.executeQuery();
            while (mealPlanTable.next()) {
                String mealPlanId = mealPlanTable.getString("id");
                String name = mealPlanTable.getString("name");
                String type = mealPlanTable.getString("type");
                String imgURL = mealPlanTable.getString("imgURL");
                String content = mealPlanTable.getString("content");
                int mealPlanStatus = mealPlanTable.getInt("status");
                List<DayPlan> dayPlanList = new ArrayList<>();
                dayPlanPst.setString(1, mealPlanId);
                ResultSet dayPlanTable = dayPlanPst.executeQuery();
                while (dayPlanTable.next()) {
                    String dayPlanId = dayPlanTable.getString("id");
                    int dayInWeek = dayPlanTable.getInt("dayInWeek");
                    int dayPlanStatus = dayPlanTable.getInt("status");
                    String mealId = dayPlanTable.getString("MealId");
//                    String id, String mealId, String mealPlanId, String customerPlanId, int dayInWeek, int status
                    DayPlan dayPlan = new DayPlan(dayPlanId, mealId, mealPlanId, null, dayInWeek, dayPlanStatus);
                    dayPlanList.add(dayPlan);
                }
                MealPlan mealPlan = new MealPlan(mealPlanId, name, type, content, imgURL, mealPlanStatus, dayPlanList);

                list.add(mealPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public int changeStatusById(String id) {
        int result = 0;
        String changeStatusSql = "update [dbo].[MealPlan]\n"
                + "set status = ?\n"
                + "where id = ?";
        String getStatusSql = "Select [status] from [dbo].[MealPlan] where id=?";
        Connection cn = null;
        try {
            cn = JDBCUtil.getConnection();
            if (cn != null) {
                PreparedStatement getStatusPst = cn.prepareCall(getStatusSql);
                getStatusPst.setString(1, id);
                ResultSet table = getStatusPst.executeQuery();
                while (table.next()) {
                    int status = table.getInt("status") == 1 ? 0 : 1;
                    PreparedStatement changeStatusPst = cn.prepareCall(changeStatusSql);
                    changeStatusPst.setInt(1, status);
                    changeStatusPst.setString(2, id);
                    result = changeStatusPst.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
