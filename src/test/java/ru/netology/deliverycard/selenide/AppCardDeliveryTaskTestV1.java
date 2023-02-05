package ru.netology.deliverycard.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTaskTestV1 {
    private String dataGenerate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullComplited() {
        open("http://localhost:9999/");
        $x("//span[@data-test-id='city']//input").setValue("Волгоград");
        String currentDate = dataGenerate(4,"dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.ARROW_LEFT), Keys.DELETE);
        $x("//span[@data-test-id='date']//input").sendKeys(currentDate);
        $x("//span[@data-test-id='name']//input").setValue("Петров-Сидоров Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79034567372");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }
}
