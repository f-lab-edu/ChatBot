package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.exception.ElementParseException;
import com.flab.fire_inform.domains.crawling.exception.InvalidUrlException;
import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.entity.Recruit.Builder;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class KakaoRecruitCrawler implements JobCrawler {

    private final RecruitMapper recruitMapper;
    private static final String KAKAO_URL = "https://careers.kakao.com/jobs";

    public KakaoRecruitCrawler(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    @Transactional
    public void crawling() {
        log.info("================== Kakao Recruits Crawling Start ===================");

        WebDriver webDriver = runWebDriver();

        List<String> recruitDetailLinks = getRecruitLinks(webDriver);

        List<Recruit> recruits = parseLinkToRecruit(webDriver, recruitDetailLinks);

        List<Recruit> recruitsToAdd = deduplicate(recruits);

        for (Recruit recruit : recruitsToAdd) {
            recruitMapper.save(recruit);
        }

        webDriver.quit();

        log.info("================== Kakao Recruits Crawling End =====================");
    }

    private WebDriver runWebDriver() {
        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // WebDriver option
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");          // 최대크기로
        options.addArguments("--headless");                 // Browser를 띄우지 않음
        options.addArguments(
            "--disable-gpu");              // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments(
            "--no-sandbox");               // Sandbox 프로세스를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함

        return new ChromeDriver(options);
    }

    private List<Recruit> deduplicate(List<Recruit> recruits) {
        List<Recruit> findRecruits = recruitMapper.findByCompany(CompanyType.KAKAO.getValue());

        Set<Recruit> recruitsSet = new HashSet<>(recruits);
        Set<Recruit> findRecruitsSet = new HashSet<>(findRecruits);

        recruitsSet.removeAll(findRecruitsSet);

        return new ArrayList<>(recruitsSet);
    }

    private List<Recruit> parseLinkToRecruit(WebDriver webDriver, List<String> recruitDetailLinks) {
        List<Recruit> result = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        for (String link : recruitDetailLinks) {

            try {
                webDriver.get(link);

                String title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//strong[@class='tit_jobs']"))).getText();

                String career = null;
                if (title.contains("신입") && title.contains("경력")) {
                    career = "경력무관";
                } else if (title.contains("경력")) {
                    career = "경력";
                } else if (title.contains("신입")) {
                    career = "신입";
                }

                String workerType = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='list_info']/dd[2]"))).getText();

                String dueDate = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='list_info']/dd[3]"))).getText();

                String address = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='list_info']/dd[4]"))).getText();

                result.add(new Builder(title, CompanyType.KAKAO.getValue(), link)
                    .career(career)
                    .dueDate(dueDate)
                    .workerType(workerType)
                    .address(address)
                    .build());
            } catch (InvalidArgumentException e) {
                webDriver.quit();
                throw new InvalidUrlException();
            } catch (WebDriverException e) {
                webDriver.quit();
                throw new ElementParseException();
            }
        }

        return result;
    }

    private List<String> getRecruitLinks(WebDriver webDriver) {
        List<String> result = new ArrayList<>();

        int currentPage = 1;

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        while (true) {
            try {
                webDriver.get(String.format("%s?page=%d", KAKAO_URL, currentPage));

                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"mArticle\"]/div/ul[@class='list_jobs']")));
            } catch (InvalidArgumentException e) {
                webDriver.quit();
                throw new InvalidUrlException();
            } catch (WebDriverException e) {  // 마지막 페이지
                break;
            }

            try {
                List<WebElement> webElements = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//ul[@class='list_jobs']/child::a")));

                for (WebElement e : webElements) {
                    String href = e.getAttribute("href");
                    if (href == null) {
                        webDriver.quit();
                        throw new ElementParseException();
                    }
                    result.add(String.format("%s", href));
                }
            } catch (WebDriverException e) {
                webDriver.quit();
                throw new ElementParseException();
            }

            currentPage += 1;
        }

        return result;
    }
}
