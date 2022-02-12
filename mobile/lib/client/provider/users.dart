import 'dart:math';

import 'package:das_angular_mobile/client/data/dummy_user.dart';
import 'package:das_angular_mobile/client/models/user.dart';
import 'package:flutter/material.dart';

class Users with ChangeNotifier {
  final Map<int, User> _items = {...DUMMY_USERS};

  List<User> get all {
    return [..._items.values];
  }

  int get count {
    return _items.length;
  }

  User byIndex(int i) {
    return _items.values.elementAt(i);
  }

  void put(User user) {
    if (user == null) {
      return;
    }

    if (user.id != null && _items.containsKey(user.id)) {
      _items.update(
        user.id!,
        (_) => User(
          id: user.id,
          lastName: user.lastName,
          firstName: user.firstName,
          cpf: user.cpf,
        ),
      );
    } else {
      final id = Random().nextInt(100);
      _items.putIfAbsent(
          id,
          () => User(
                id: id,
                lastName: user.lastName,
                firstName: user.firstName,
                cpf: user.cpf,
              ));
    }
    notifyListeners();
  }

  void remove(User user) {
    if (user != null && user.id != null) {
      _items.remove(user.id);
      notifyListeners();
    }
  }
}
