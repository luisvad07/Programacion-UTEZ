import 'package:flutter/material.dart';
import 'package:intl/intl.dart'; // Importa intl
import 'package:mac_morelos_movil_flutter/kernel/services/events_service.dart';

class EventsList extends StatefulWidget {
  const EventsList({Key? key}) : super(key: key);

  @override
  State<EventsList> createState() => _EventsListState();
}

class _EventsListState extends State<EventsList> {
  late EventsService _eventsService;
  List<Event> _activeEvents = [];
  List<Event> _upcomingEvents = [];
  bool _isLoadingActive = true;
  bool _isLoadingUpcoming = true;
  String _errorMessageActive = '';
  String _errorMessageUpcoming = '';

  @override
  void initState() {
    super.initState();
    _eventsService = EventsService(
        baseUrl:
            'http://localhost:8081/mac-morelos-api'); // Reemplaza con tu URL base
    _loadEvents();
  }

  Future<void> _loadEvents() async {
    await _loadActiveEvents();
    await _loadUpcomingEvents();
  }

  Future<void> _loadActiveEvents() async {
    setState(() {
      _isLoadingActive = true;
      _errorMessageActive = '';
    });
    try {
      final events = await _eventsService.getActiveEvents();
      setState(() {
        _activeEvents = events;
        _isLoadingActive = false;
      });
    } catch (e) {
      setState(() {
        _errorMessageActive = e.toString();
        _isLoadingActive = false;
      });
    }
  }

  Future<void> _loadUpcomingEvents() async {
    setState(() {
      _isLoadingUpcoming = true;
      _errorMessageUpcoming = '';
    });
    try {
      final events = await _eventsService.getUpcomingEvents();
      setState(() {
        _upcomingEvents = events;
        _isLoadingUpcoming = false;
      });
    } catch (e) {
      setState(() {
        _errorMessageUpcoming = e.toString();
        _isLoadingUpcoming = false;
      });
    }
  }

  void _showEventDetailsModal(BuildContext context, Event event) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(event.name),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text('Description: ${event.description}'),
                const SizedBox(height: 8),
                Text(
                    'Fecha y Hora de Inicio: ${DateFormat('dd-MM-yyyy HH:mm').format(event.startDate)}'),
                const SizedBox(height: 8),
                Text(
                    'Fecha y Hora de Fin: ${DateFormat('dd-MM-yyyy HH:mm').format(event.endDate)}'),
                const SizedBox(height: 8),
                Text('Lugar del Evento: ${event.location}'),
                const SizedBox(height: 8),
                Text('Dirección: ${event.address.street} ${event.address.number}, ${event.address.neighborhood}, ${event.address.city}, ${event.address.state}, ${event.address.country}'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Cerrar'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          'Eventos Activos',
          style: Theme.of(context).textTheme.headlineSmall,
        ),
        if (_isLoadingActive)
          const CircularProgressIndicator()
        else if (_errorMessageActive.isNotEmpty)
          Text('Error: $_errorMessageActive')
        else if (_activeEvents.isEmpty)
          const Text('No hay eventos activos.')
        else
          ListView.builder(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            itemCount: _activeEvents.length,
            itemBuilder: (context, index) {
              final event = _activeEvents[index];
              return Card(
                child: ListTile(
                  title: Text(event.name),
                  subtitle: Text(event.description),
                  onTap: () => _showEventDetailsModal(context, event),
                ),
              );
            },
          ),
        const SizedBox(height: 20),
        Text(
          'Eventos Próximos',
          style: Theme.of(context).textTheme.headlineSmall,
        ),
        if (_isLoadingUpcoming)
          const CircularProgressIndicator()
        else if (_errorMessageUpcoming.isNotEmpty)
          Text('Error: $_errorMessageUpcoming')
        else if (_upcomingEvents.isEmpty)
          const Text('No hay eventos próximos.')
        else
          ListView.builder(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            itemCount: _upcomingEvents.length,
            itemBuilder: (context, index) {
              final event = _upcomingEvents[index];
              return Card(
                child: ListTile(
                  title: Text(event.name),
                  subtitle: Text(event.description),
                  onTap: () => _showEventDetailsModal(context, event),
                ),
              );
            },
          ),
      ],
    );
  }
}