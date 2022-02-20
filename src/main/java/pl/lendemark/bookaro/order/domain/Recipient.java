package pl.lendemark.bookaro.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Recipient {
    private String name;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String email;
}
