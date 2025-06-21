import 'package:equatable/equatable.dart';
import '../../data/models/people_model.dart';

abstract class PeopleState extends Equatable {
  const PeopleState();

  @override
  List<Object?> get props => [];
}

class PeopleInitial extends PeopleState {}

class PeopleLoading extends PeopleState {}

class PeopleLoaded extends PeopleState {
  final List<PeopleModel> peopleList;

  const PeopleLoaded(this.peopleList);

  @override
  List<Object?> get props => [peopleList];
}

class PeopleError extends PeopleState {
  final String message;

  const PeopleError(this.message);

  @override
  List<Object?> get props => [message];
}
