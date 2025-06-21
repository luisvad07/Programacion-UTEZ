class AddressModel {
  final String? street;
  final String city;
  final String state;
  final String postalCode;

  AddressModel({this.street, required this.city, required this.state, required this.postalCode});

  factory AddressModel.fromJson(Map<String, dynamic> json) {
    return AddressModel(
      street: json['street'],
      city: json['city'],
      state: json['state'],
      postalCode: json['postalCode'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "street": street,
      "city": city,
      "state": state,
      "postalCode": postalCode,
    };
  }
}