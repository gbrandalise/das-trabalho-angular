class User {
  final int? id;
  final String cpf;
  final String firstName;
  final String lastName;

  const User({
    this.id,
    required this.cpf,
    required this.firstName,
    required this.lastName,
  });
}
