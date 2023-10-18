import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IAppQuestionVideoInfoMySuffix, defaultValue } from 'app/shared/model/app-question-video-info-my-suffix.model';

const initialState: EntityState<IAppQuestionVideoInfoMySuffix> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/app-question-video-infos';

// Actions

export const getEntities = createAsyncThunk('appQuestionVideoInfo/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IAppQuestionVideoInfoMySuffix[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'appQuestionVideoInfo/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IAppQuestionVideoInfoMySuffix>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'appQuestionVideoInfo/create_entity',
  async (entity: IAppQuestionVideoInfoMySuffix, thunkAPI) => {
    const result = await axios.post<IAppQuestionVideoInfoMySuffix>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'appQuestionVideoInfo/update_entity',
  async (entity: IAppQuestionVideoInfoMySuffix, thunkAPI) => {
    const result = await axios.put<IAppQuestionVideoInfoMySuffix>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'appQuestionVideoInfo/partial_update_entity',
  async (entity: IAppQuestionVideoInfoMySuffix, thunkAPI) => {
    const result = await axios.patch<IAppQuestionVideoInfoMySuffix>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'appQuestionVideoInfo/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IAppQuestionVideoInfoMySuffix>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const AppQuestionVideoInfoMySuffixSlice = createEntitySlice({
  name: 'appQuestionVideoInfo',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = AppQuestionVideoInfoMySuffixSlice.actions;

// Reducer
export default AppQuestionVideoInfoMySuffixSlice.reducer;
