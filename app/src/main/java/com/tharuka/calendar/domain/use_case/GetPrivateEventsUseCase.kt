package com.tharuka.calendar.domain.use_case

import com.tharuka.calendar.common.Resource
import com.tharuka.calendar.domain.model.PrivateEvent
import com.tharuka.calendar.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPrivateEventsUseCase @Inject constructor(private val repository: CalendarRepository) {

    operator fun invoke(): Flow<Resource<List<PrivateEvent>>>{
        return flow{
            try {
                emit(Resource.Success(data = repository.getPrivateEvents()))
            }catch (exception:Exception){
                emit(Resource.Error(message = "Something went while getting your events"))
            }
        }
    }

}