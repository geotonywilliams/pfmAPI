package com.example.pfmapi.service;

import com.example.pfmapi.models.FinancialRecord;
import com.example.pfmapi.models.PeriodTransData;
import com.example.pfmapi.models.TransData;
import com.example.pfmapi.models.monthlySummary;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class pfmService implements IpfmService {
    NamedParameterJdbcTemplate queryRunner;
    private static Logger logger = LogManager.getLogger(pfmService.class);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public FinancialRecord GetRecord(String id, String month) throws Exception {

        try {
            int currentYear = Integer.parseInt(month.substring(0, 4));
            int currentMonth = Integer.parseInt(month.substring(4));

            LocalDate currentDate = LocalDate.of(currentYear, currentMonth, 1);
            LocalDate startDate = currentDate.minusYears(1);
            LocalDate endDate = currentDate.minusDays(1);

            monthlySummary currentSummary = queryRunner.queryForObject("SELECT * FROM pfm_monthly_summaries where ORGKEY = :orgkey and YEAR_MONTH = :month", new MapSqlParameterSource("orgkey", id).addValue("month", month), (rs, m) -> MapResult(rs));


            DateTimeFormatter dF = DateTimeFormatter.ofPattern("yyyyMM");
            List<monthlySummary> annualData = queryRunner.query("SELECT * FROM pfm_monthly_summaries where ORGKEY = :orgkey and YEAR_MONTH>=:start and YEAR_MONTH<=:end order by YEAR_MONTH asc", new MapSqlParameterSource("orgkey", id).addValue("start", dF.format(startDate)).addValue("end", dF.format(endDate)), (rs, m) -> MapResult(rs));


            FinancialRecord record = new FinancialRecord();
            record.setIdentifier(id);
            record.setMonth(month);

            List<TransData> currentData = new ArrayList<>();
            double scale = Math.pow(10, 2);

            currentData.add(new TransData("TOTAL SPENT", Math.round(currentSummary.getTOTALDEBITS()*scale)/scale, 0d));
            currentData.add(new TransData("TOTAL RECEIVED", Math.round(currentSummary.getTOTALCREDITS() * scale) / scale, 0d));
            currentData.add(new TransData("Account To Wallet", Math.round(currentSummary.getACCOUNT2WALLET() * scale) / scale, 0d));
            currentData.add(new TransData("ATM", Math.round(currentSummary.getATM() * scale) / scale, 0d));
            currentData.add(new TransData("Branch", Math.round(currentSummary.getBRANCH() * scale) / scale, 0d));
            currentData.add(new TransData("Digital", Math.round(currentSummary.getDIGITAL() * scale) / scale, 0d));
            currentData.add(new TransData("POS", Math.round(currentSummary.getPOS() * scale) / scale, 0d));
            currentData.add(new TransData("Others", Math.round(currentSummary.getOTHERS() * scale) / scale, 0d));


            List<PeriodTransData> spendByChannel = new ArrayList<>();
            List<PeriodTransData> trendOfSpendRec = new ArrayList<>();

            double sixMonthSpend = currentSummary.getTOTALDEBITS();
            double sixMonthRec = currentSummary.getTOTALCREDITS();

            int counter=12;

            for (monthlySummary oneMonth : annualData) {
                List<TransData> channelData = new ArrayList<>();
                channelData.add(new TransData("Account To Wallet", Math.round(oneMonth.getACCOUNT2WALLET() * scale) / scale, 0d));
                channelData.add(new TransData("ATM", Math.round(oneMonth.getATM() * scale) / scale, 0d));
                channelData.add(new TransData("Branch", Math.round(oneMonth.getBRANCH() * scale) / scale, 0d));
                channelData.add(new TransData("Digital", Math.round(oneMonth.getDIGITAL() * scale) / scale, 0d));
                channelData.add(new TransData("Loans", Math.round(oneMonth.getLOAN() * scale) / scale, 0d));
                channelData.add(new TransData("POS", Math.round(oneMonth.getPOS() * scale) / scale, 0d));
                channelData.add(new TransData("Others", Math.round(oneMonth.getOTHERS() * scale) / scale, 0d));

                spendByChannel.add(new PeriodTransData(MonthName(oneMonth.getYEAR_MONTH()), channelData));


                List<TransData> trendData = new ArrayList<>();
                trendData.add(new TransData("Spent", Math.round(oneMonth.getTOTALDEBITS() * scale) / scale, 0d));
                trendData.add(new TransData("Received", Math.round(oneMonth.getTOTALCREDITS() * scale) / scale, 0d));

                trendOfSpendRec.add(new PeriodTransData(MonthName(oneMonth.getYEAR_MONTH()), trendData));

                if(counter>=8){//Last 5months
                    sixMonthSpend+=oneMonth.getTOTALDEBITS();
                    sixMonthRec+=oneMonth.getTOTALCREDITS();
                }
                counter--;
            }

            currentData.add(new TransData("AVERAGE SPEND", Math.round(sixMonthSpend/6 * scale) / scale, 0d));
            currentData.add(new TransData("AVERAGE RECEIVED", Math.round(sixMonthRec/6 * scale) / scale, 0d));
            record.setCurrentData(currentData);

            record.setSpendByChannel(spendByChannel);
            record.setTrendOfSpendReceipt(trendOfSpendRec);

            return record;
        }
        catch (Exception e) {

            logger.debug("{}: e.getMessage()", "PFMService");
        }
        return new FinancialRecord();
    }

    private monthlySummary MapResult(ResultSet rs) throws SQLException {
        monthlySummary ms = new monthlySummary();

        ms.setID(rs.getString("ID"));
        ms.setORGKEY(rs.getString("ORGKEY"));
        ms.setYEAR_MONTH(rs.getString("YEAR_MONTH"));
        ms.setSEGMENT(rs.getString("SEGMENT"));
        ms.setNO_OF_ACCOUNTS(rs.getString("NO_OF_ACCOUNTS"));
        ms.setCUST_FIRST_NAME(rs.getString("CUST_FIRST_NAME"));
        ms.setCUST_LAST_NAME(rs.getString("CUST_LAST_NAME"));
        ms.setPREFERREDPHONE(rs.getString("PREFERREDPHONE"));
        ms.setEMAIL(rs.getString("EMAIL"));
        ms.setACCOUNT2WALLET(rs.getDouble("ACCOUNT2WALLET"));
        ms.setLOAN(rs.getDouble("LOAN"));
        ms.setATM(rs.getDouble("ATM"));
        ms.setPOS(rs.getDouble("POS"));
        ms.setBRANCH(rs.getDouble("BRANCH"));
        ms.setDIGITAL(rs.getDouble("DIGITAL"));
        ms.setTOTALDEBITS(rs.getDouble("TOTALDEBITS"));
        ms.setTOTALCREDITS(rs.getDouble("TOTALCREDITS"));
        ms.setOTHERS(rs.getDouble("OTHERS"));
        ms.setECOMMERCE(rs.getDouble("ECOMMERCE"));
        ms.setHEALTH(rs.getDouble("HEALTH"));
        ms.setMISC(rs.getDouble("MISC"));
        ms.setRESTAURANTS(rs.getDouble("RESTAURANTS"));
        ms.setRETAIL(rs.getDouble("RETAIL"));
        ms.setTRAVEL(rs.getDouble("TRAVEL"));

        return ms;

    }

    private String MonthName(String yearMonth){
        switch(yearMonth.substring(4 )){
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default: return "Jan";
        }
    }
}
