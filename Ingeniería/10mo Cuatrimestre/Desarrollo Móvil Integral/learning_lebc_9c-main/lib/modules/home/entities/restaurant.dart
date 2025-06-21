import 'package:cloud_firestore/cloud_firestore.dart';

class Restaurant {
  final String _titulo;
  final String _description;
  final List<String>_images;
  final double _rating;
  final int _count;
  final GeoPoint _location;

  Restaurant(this._titulo, this._description, this._images, this._rating, this._count, this._location);

  String get titulo => _titulo;
  String get description => _description;
  List<String> get images => _images;
  double get rating => _rating;
  int get count => _count;
  GeoPoint get location => _location;

}
